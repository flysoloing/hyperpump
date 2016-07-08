/**
 * Created by Administrator on 2016/6/26.
 */
//$("#navTest").click(function() {
//    showPageContent("navTest");
//});

$("li a").click(function() {
    var path = $(this).attr("id");
    if(path == undefined || path == "" || path == null) {
        return;
    }
//    $(this).siblings(".active").removeClass("active");
//    $(this).addClass("active");
    showPageContent(path);
});

function showPageContent(path) {
    var loadUrl = "/" + path + "?temp="+new Date().getTime();
    $.ajax({
        url: loadUrl,
        type: "GET",
        dataType: "json",
        scriptCharset: "utf-8",
        success: function(result) {
            $("#page-wrapper").replaceWith(result.result);
        },
        error: function(xhr, status, errorThrown) {
            console.log("Error: " + errorThrown);
            console.log("Status: " + status);
            console.dir(xhr);
        },
        complete: function(xhr, status) {
            console.log("Status: " + status);
        }
    });
}