<%-- 
    Document   : map
    Created on : Sep 27, 2014, 12:16:00 PM
    Author     : Renan
--%>

<!DOCTYPE html>
<html>
  <head>
    <style type="text/css">
      html, body, #map-canvas { height: 100%; margin: 0; padding: 0;}
    </style>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCu7-cBtt66uou1SkZX09GaCSYusE1pe-M&libraries=drawing">
    </script>
    <script type="text/javascript">
        var map;
        var ufrgsPoa = new google.maps.LatLng(-30.071881, -51.120694);
        
        function getPoligonCoord (event) {
            if (event.type == google.maps.drawing.OverlayType.POLYGON) {
                
                var polygon = event.overlay;
                //polygon -- AQUI
                var path = polygon.getPath();

                var bounds = new google.maps.LatLngBounds();
                var contentString = 'Coordenadas:</br>';
                
                for (i = 0; i < path.length; ++i) {
                    var point = path.getAt(i);
                    bounds.extend(point);
                    
                    contentString += point.toString()+'\n';
                }
                
                var marker = new google.maps.Marker({
                    position: bounds.getCenter(),
                    map: map,
                    icon: 'images/beachflag.png',
                    title:"Hello World!"
                });
                
                var infowindow = new google.maps.InfoWindow({
                    content: contentString
                });
                
                infowindow.open(map, marker);
            }
        }
        
        function center() {
            map.setCenter(ufrgsPoa);
        }
      
        function initialize() {
            var mapDiv = document.getElementById('map-canvas');
            var mapOptions = {
              center: ufrgsPoa,
              zoom: 15
            };
            map = new google.maps.Map(mapDiv, mapOptions);

            google.maps.event.addListener(map, 'rightclick', center);
            
            var drawingManager = new google.maps.drawing.DrawingManager({
                drawingMode: google.maps.drawing.OverlayType.MARKER,
                drawingControl: true,
                drawingControlOptions: {
                  position: google.maps.ControlPosition.TOP_CENTER,
                  drawingModes: [
                    google.maps.drawing.OverlayType.MARKER,
                    google.maps.drawing.OverlayType.CIRCLE,
                    google.maps.drawing.OverlayType.POLYGON,
                    google.maps.drawing.OverlayType.POLYLINE,
                    google.maps.drawing.OverlayType.RECTANGLE
                  ]
                },
                markerOptions: {
                  icon: 'images/beachflag.png'
                },
                circleOptions: {
                  fillColor: '#ffff00',
                  fillOpacity: 1,
                  strokeWeight: 5,
                  clickable: false,
                  editable: true,
                  zIndex: 1
                }
            });
            drawingManager.setMap(map);
            
            google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
                getPoligonCoord(event);
            });
        }
      
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  </head>
  <body>
<div id="map-canvas"></div>
  </body>
</html>

<%-- 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
--%>
