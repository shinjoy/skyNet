function getLatLng() {
    while (m = gMarkersOnTheMap.shift()) {
        m.setMap(null);
        delete m
    }
    gGeocoder.geocode({
        address: document.getElementById('address').value
    }, function(a, b) {
        if ((b != google.maps.GeocoderStatus.OK) || (a.length == 0)) {
            alert('찾을 수 없음')
        } else {
            var c = new google.maps.LatLngBounds();
            for (var r in a) {
                if (a[r].geometry) {
                    gMarkersOnTheMap.push(new MarkerWithAddress({
                        position: a[r].geometry.location,
                        map: myMap,
                        draggable: true
                    }));
                    c.extend(a[r].geometry.location)
                }
            }
            myMap.fitBounds(c)
        }
    })
}

function MarkerWithAddress() {
    var m = new google.maps.Marker(arguments[0]);
    var f = new google.maps.InfoWindow();
    google.maps.event.addListener(m, 'click', function(a) {
        if (f.getContent() == null) {
            openInfoWindow()
        }
    });
    google.maps.event.addListener(m, 'dragend', function(a) {
        if (f.getContent()) {
            openInfoWindow()
        }
    });
    google.maps.event.addListener(m, 'dblclick', function(a) {
        myMap.panTo(m.getPosition());
        myMap.setZoom(myMap.getZoom() + 1)
    });
    google.maps.event.addListener(f, 'closeclick', function() {
        f.setContent(null)
    });

    function openInfoWindow() {
        if (f.getContent()) {
            f.close()
        }
        gGeocoder.geocode({
            latLng: m.getPosition()
        }, function(c, d) {
            var e = '(위도,경도)=<br>' + m.getPosition().toString() + '<br>';
            // 폼에 위도 경도 데이터 보내기
            setLatLng(m.getPosition());
            if (d == google.maps.GeocoderStatus.OK) {
                e += c[0].formatted_address.replace(/^日本, /, '')
            }
            gElevation.getElevationForLocations({
                locations: [m.getPosition()]
            }, function(a, b) {
                if (b == google.maps.ElevationStatus.OK) {
                    if (a[0].elevation) {
                        e += "<br>【표고：" + a[0].elevation + " m】 "
                    }
                }
                f.setOptions({
                    content: e
                });
                f.open(m.getMap(), m)
            })
        })
    }
    this.setMap = function() {
        m.setMap(arguments[0]);
        if (f.getContent()) {
            f.close();
            f.setContent(null)
        }
    };
}

//폼에 위도 경도 데이터 보내기
function setLatLng(pos) {
	////console.log(pos);
	firmForm.latitude.value = pos.lat();
	firmForm.longitude.value = pos.lng();
}

var myMap;
var gGeocoder;
var gElevation;
var gMarkersOnTheMap;
var myMarker;
window.onload = function() {
	setMap();
};
  
  function setMap() {
	  
		// 위치 선정
	var la = 37.56;
	var lo = 126.97;
	if (!(firmForm.latitude == undefined || firmForm.latitude.value == "0.0") ) {
		la = firmForm.latitude.value;
	}
	if (!(firmForm.longitude == undefined || firmForm.longitude.value == "0.0") ) {
		lo = firmForm.longitude.value;
	}
	
	// 구글맵 그리기
    myMap = new google.maps.Map(document.getElementById('map'), {
        zoom: 14,
        center: new google.maps.LatLng(la, lo),
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: true,
        scaleControl: true,
        navigationControlOptions: true
    });
    gGeocoder = new google.maps.Geocoder();
    gElevation = new google.maps.ElevationService();
    gMarkersOnTheMap = new Array();
    
    // 최초 로딩시 마커 그리기
	var markLocation = new google.maps.LatLng(la, lo); // 마커가 위치할 위도와 경도
	myMarker = new MarkerWithAddress({
        position: markLocation,
        map: myMap,
        draggable: true
    });
    
	// 지도 클릭시 마커 그리기
    google.maps.event.addListener(myMap, 'click', function(a) {
    	setLatLng(a.latLng);
    	// 기존 마커 지우고 새로 그리기
    	myMarker.setMap(null);
    	myMarker = new MarkerWithAddress({
            position: a.latLng,
            map: myMap,
            draggable: true
        })

    	// 기존 마커 이외에 추가하기
    	/*
        gMarkersOnTheMap.push(new MarkerWithAddress({
            position: a.latLng,
            map: myMap,
            draggable: true
        }))
        */
    })
  
  }