function initMap() {
    var brasil = {
        lat : -14.239183,
        lng : -51.913726
    };

    var map = new google.maps.Map(document.getElementById('map'), {
        center : brasil,
        scrollwheel : false,
        zoom : 4
    });

    for (index = 0; index < students.length; ++index) {
        var lat = students[index].contact.coordinates[0];
        var lon = students[index].contact.coordinates[1];
        var coordinates = {
                lat : lat,
                lng : lon
            };
        var marker = new google.maps.Marker({
            position : coordinates,
            label: students[index].name
        });
        marker.setMap(map);
    }
}