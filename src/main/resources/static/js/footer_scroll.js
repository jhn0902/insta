(function($) {
    $.fn.hasVerticalScrollbar = function() {
        return this.get(0).scrollHeight > this.height();
    }
})(jQuery);

$(function(){
    if ($('html').hasVerticalScrollbar()) {
        $("#footer").attr("style", "display:block;");
    }
});
 $(window).scroll(function() {
    if($(document).scrollTop() + $(window).innerHeight() >= $('html').height() - 20) {
        $("#footer").attr("style", "display:block;");
    } else {
        $("#footer").hide();
    }
});
