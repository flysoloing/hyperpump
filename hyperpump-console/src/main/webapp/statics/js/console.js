/**
 * Created by Administrator on 2016/6/26.
 */
$("#navTest").click(function() {
    showPageContent("/navTest");
});

function showPageContent() {
    var loadUrl = "/navTest?temp="+new Date().getTime();
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