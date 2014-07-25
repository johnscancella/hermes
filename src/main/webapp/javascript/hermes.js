$(document).ready(function(){
    // Variables
    var objMain = $('#main');
 
    // Show sidebar
    function showSidebar(){
        objMain.addClass('use-sidebar');
    }
 
    // Hide sidebar
    function hideSidebar(){
        objMain.removeClass('use-sidebar');
    }
 
    // Sidebar separator
    var objSeparator = $('#separator');
 
    objSeparator.click(function(e){
        e.preventDefault();
        if ( objMain.hasClass('use-sidebar') ){
            hideSidebar();
        }
        else {
            showSidebar();
        }
    }).css('height', '302px');
});