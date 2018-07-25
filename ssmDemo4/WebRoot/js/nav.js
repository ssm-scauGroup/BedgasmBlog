$(document).ready(function () {
    $('.navbar').find('a').each(function () {
            if (this.href == document.location.href || document.location.href.search(this.href) >= 0) {
                $(this).parent().addClass('active'); // this.className = 'active';
            }
        });

});