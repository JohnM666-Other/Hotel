$(document).ready(function() {
    $(".hotel").click(function(event) {
        var hotelId = $(this).attr("data-hotel-id");
        document.location.replace("/hotels/" + hotelId);
    })
});