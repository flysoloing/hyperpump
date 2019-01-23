package com.flysoloing.hyperpump.console.controller;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author laitao
 * @date 2016-06-26 15:35:18
 */
public class ViewResolverSupport extends WebApplicationObjectSupport {

    /** Default content type. Overridable as bean property. */
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=ISO-8859-1";

    /** Initial size for the temporary output byte array (if any) */
    private static final int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;

    private String beanName;

    private String contentType = DEFAULT_CONTENT_TYPE;

    private String requestContextAttribute;

    private final Map<String, Object> staticAttributes = new LinkedHashMap<String, Object>();

    private boolean exposePathVariables = true;

    private boolean exposeContextBeansAsAttributes = false;

    private Set<String> exposedContextBeanNames;

    /**
     * Variable name of the RequestContext instance in the template model,
     * available to Spring's macros: e.g. for creating BindStatus objects.
     */
    public static final String SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE = "springMacroRequestContext";

    private boolean exposeRequestAttributes = false;

    private boolean allowRequestOverride = false;

    private boolean exposeSessionAttributes = false;

    private boolean allowSessionOverride = false;

    private boolean exposeSpringMacroHelpers = true;

    private Map<String, Class<?>> toolAttributes;

    private String dateToolAttribute;

    private String numberToolAttribute;

    private String encoding;

    private boolean cacheTemplate = false;

    private VelocityEngine velocityEngine;

    private Template template;

    public String render(String velocityConfigurerName, String name, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        WebApplicationContext webApplicationContext = getWebApplicationContext();
        VelocityConfigurer velocityConfigurer = (VelocityConfigurer)webApplicationContext.getBean(velocityConfigurerName);
        VelocityEngine velocityEngine = velocityConfigurer.getVelocityEngine();
        setVelocityEngine(velocityEngine);
        Template template = velocityEngine.getTemplate(name);
        setTemplate(template);
        Map<String, Object> mergedModel = createMergedOutputModel(model, request, response);
        //prepareResponse(request, response);
        try {
            return renderMergedOutputModel(mergedModel, getRequestToExpose(request), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> createMergedOutputModel(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {

        @SuppressWarnings("unchecked")
        Map<String, Object> pathVars = (this.exposePathVariables ? (Map<String, Object>) request.getAttribute(View.PATH_VARIABLES) : null);

        // Consolidate static and dynamic model attributes.
        int size = this.staticAttributes.size();
        size += (model != null ? model.size() : 0);
        size += (pathVars != null ? pathVars.size() : 0);

        Map<String, Object> mergedModel = new LinkedHashMap<String, Object>(size);
        mergedModel.putAll(this.staticAttributes);
        if (pathVars != null) {
            mergedModel.putAll(pathVars);
        }
        if (model != null) {
            mergedModel.putAll(model);
        }

        // Expose RequestContext?
        if (this.requestContextAttribute != null) {
            mergedModel.put(this.requestContextAttribute, createRequestContext(request, response, mergedModel));
        }

        return mergedModel;
    }

    private RequestContext createRequestContext(
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        return new RequestContext(request, response, getServletContext(), model);
    }

    private HttpServletRequest getRequestToExpose(HttpServletRequest originalRequest) {
        if (this.exposeContextBeansAsAttributes || this.exposedContextBeanNames != null) {
            return new ContextExposingHttpServletRequest(
                    originalRequest, getWebApplicationContext(), this.exposedContextBeanNames);
        }
        return originalRequest;
    }

    private String renderMergedOutputModel(
            Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (this.exposeRequestAttributes) {
            for (Enumeration<String> en = request.getAttributeNames(); en.hasMoreElements();) {
                String attribute = en.nextElement();
                if (model.containsKey(attribute) && !this.allowRequestOverride) {
                    throw new ServletException("Cannot expose request attribute '" + attribute +
                            "' because of an existing model object of the same name");
                }
                Object attributeValue = request.getAttribute(attribute);
                if (logger.isDebugEnabled()) {
                    logger.debug("Exposing request attribute '" + attribute +
                            "' with value [" + attributeValue + "] to model");
                }
                model.put(attribute, attributeValue);
            }
        }

        if (this.exposeSessionAttributes) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                for (Enumeration<String> en = session.getAttributeNames(); en.hasMoreElements();) {
                    String attribute = en.nextElement();
                    if (model.containsKey(attribute) && !this.allowSessionOverride) {
                        throw new ServletException("Cannot expose session attribute '" + attribute +
                                "' because of an existing model object of the same name");
                    }
                    Object attributeValue = session.getAttribute(attribute);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Exposing session attribute '" + attribute +
                                "' with value [" + attributeValue + "] to model");
                    }
                    model.put(attribute, attributeValue);
                }
            }
        }

        if (this.exposeSpringMacroHelpers) {
            if (model.containsKey(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE)) {
                throw new ServletException(
                        "Cannot expose bind macro helper '" + SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE +
                                "' because of an existing model object of the same name");
            }
            // Expose RequestContext instance for Spring macros.
            model.put(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE,
                    new RequestContext(request, response, getServletContext(), model));
        }

        //applyContentType(response);

        return renderMergedTemplateModel(model, request, response);
    }

    private String renderMergedTemplateModel(
            Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //exposeHelpers(model, request);

        Context velocityContext = createVelocityContext(model, request, response);
        //exposeHelpers(velocityContext, request, response);
        exposeToolAttributes(velocityContext, request);

        return doRender(velocityContext, response);
    }

    private Context createVelocityContext(
            Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return createVelocityContext(model);
    }

    private Context createVelocityContext(Map<String, Object> model) throws Exception {
        return new VelocityContext(model);
    }

    private void exposeToolAttributes(Context velocityContext, HttpServletRequest request) throws Exception {
        // Expose generic attributes.
        if (this.toolAttributes != null) {
            for (Map.Entry<String, Class<?>> entry : this.toolAttributes.entrySet()) {
                String attributeName = entry.getKey();
                Class<?> toolClass = entry.getValue();
                try {
                    Object tool = toolClass.newInstance();
                    initTool(tool, velocityContext);
                    velocityContext.put(attributeName, tool);
                }
                catch (Exception ex) {
                    throw new NestedServletException("Could not instantiate Velocity tool '" + attributeName + "'", ex);
                }
            }
        }

        // Expose locale-aware DateTool/NumberTool attributes.
        if (this.dateToolAttribute != null || this.numberToolAttribute != null) {
            if (this.dateToolAttribute != null) {
                velocityContext.put(this.dateToolAttribute, new LocaleAwareDateTool(request));
            }
            if (this.numberToolAttribute != null) {
                velocityContext.put(this.numberToolAttribute, new LocaleAwareNumberTool(request));
            }
        }
    }

    private void initTool(Object tool, Context velocityContext) throws Exception {
        // Velocity Tools 1.3: a class-level "init(Object)" method.
        Method initMethod = ClassUtils.getMethodIfAvailable(tool.getClass(), "init", Object.class);
        if (initMethod != null) {
            ReflectionUtils.invokeMethod(initMethod, tool, velocityContext);
        }
    }

    private String doRender(Context context, HttpServletResponse response) throws Exception {
        return mergeTemplate(getTemplate(), context, response);
    }

    private String mergeTemplate(Template template, Context context, HttpServletResponse response) throws Exception {
        try {
            StringWriter stringWriter = new StringWriter();
            template.merge(context, stringWriter);
            return stringWriter.toString();
        }
        catch (MethodInvocationException ex) {
            Throwable cause = ex.getWrappedThrowable();
            throw new NestedServletException(
                    "Method invocation failed during rendering of Velocity view with name '" +
                            getBeanName() + "': " + ex.getMessage() + "; reference [" + ex.getReferenceName() +
                            "], method '" + ex.getMethodName() + "'",
                    cause==null ? ex : cause);
        }
    }

    /**
     * Subclass of DateTool from Velocity Tools, using a Spring-resolved
     * Locale and TimeZone instead of the default Locale.
     * @see org.springframework.web.servlet.support.RequestContextUtils#getLocale
     * @see org.springframework.web.servlet.support.RequestContextUtils#getTimeZone
     */
    private static class LocaleAwareDateTool extends DateTool {

        private final HttpServletRequest request;

        public LocaleAwareDateTool(HttpServletRequest request) {
            this.request = request;
        }

        @Override
        public Locale getLocale() {
            return RequestContextUtils.getLocale(this.request);
        }

        @Override
        public TimeZone getTimeZone() {
            TimeZone timeZone = RequestContextUtils.getTimeZone(this.request);
            return (timeZone != null ? timeZone : super.getTimeZone());
        }
    }

    /**
     * Subclass of NumberTool from Velocity Tools, using a Spring-resolved
     * Locale instead of the default Locale.
     * @see org.springframework.web.servlet.support.RequestContextUtils#getLocale
     */
    private static class LocaleAwareNumberTool extends NumberTool {

        private final HttpServletRequest request;

        public LocaleAwareNumberTool(HttpServletRequest request) {
            this.request = request;
        }

        @Override
        public Locale getLocale() {
            return RequestContextUtils.getLocale(this.request);
        }
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRequestContextAttribute() {
        return requestContextAttribute;
    }

    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    public Map<String, Object> getStaticAttributes() {
        return staticAttributes;
    }

    public boolean isExposePathVariables() {
        return exposePathVariables;
    }

    public void setExposePathVariables(boolean exposePathVariables) {
        this.exposePathVariables = exposePathVariables;
    }

    public boolean isExposeContextBeansAsAttributes() {
        return exposeContextBeansAsAttributes;
    }

    public void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
        this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
    }

    public Set<String> getExposedContextBeanNames() {
        return exposedContextBeanNames;
    }

    public void setExposedContextBeanNames(Set<String> exposedContextBeanNames) {
        this.exposedContextBeanNames = exposedContextBeanNames;
    }

    public boolean isExposeRequestAttributes() {
        return exposeRequestAttributes;
    }

    public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
        this.exposeRequestAttributes = exposeRequestAttributes;
    }

    public boolean isAllowRequestOverride() {
        return allowRequestOverride;
    }

    public void setAllowRequestOverride(boolean allowRequestOverride) {
        this.allowRequestOverride = allowRequestOverride;
    }

    public boolean isExposeSessionAttributes() {
        return exposeSessionAttributes;
    }

    public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
        this.exposeSessionAttributes = exposeSessionAttributes;
    }

    public boolean isAllowSessionOverride() {
        return allowSessionOverride;
    }

    public void setAllowSessionOverride(boolean allowSessionOverride) {
        this.allowSessionOverride = allowSessionOverride;
    }

    public boolean isExposeSpringMacroHelpers() {
        return exposeSpringMacroHelpers;
    }

    public void setExposeSpringMacroHelpers(boolean exposeSpringMacroHelpers) {
        this.exposeSpringMacroHelpers = exposeSpringMacroHelpers;
    }

    public Map<String, Class<?>> getToolAttributes() {
        return toolAttributes;
    }

    public void setToolAttributes(Map<String, Class<?>> toolAttributes) {
        this.toolAttributes = toolAttributes;
    }

    public String getDateToolAttribute() {
        return dateToolAttribute;
    }

    public void setDateToolAttribute(String dateToolAttribute) {
        this.dateToolAttribute = dateToolAttribute;
    }

    public String getNumberToolAttribute() {
        return numberToolAttribute;
    }

    public void setNumberToolAttribute(String numberToolAttribute) {
        this.numberToolAttribute = numberToolAttribute;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isCacheTemplate() {
        return cacheTemplate;
    }

    public void setCacheTemplate(boolean cacheTemplate) {
        this.cacheTemplate = cacheTemplate;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
