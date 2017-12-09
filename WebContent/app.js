var app = angular.module('BMTCApp', []);
app.controller("bmtcController", function($http, $scope) {
    var self = this;
    this.routeName = 'K_4';
    this.selectedBusStop = {};
    this.sttButton;
    this.name = '';
    this.busStops = [];
    this.busesData = [];
    this.date = '18/06/2016';
    this.startTime = "18:15:00";
    this.endTime = "20:00:00";
    this.sourceStopName = '';
    this.destinationStopName = '';
    this.selectedOption = '';
    this.direction = '';

    this.myStyle = {};

    //radio button for find direction
    this.selectDirection = function() {

        var self = this;
        if (this.selectedOption === "source") {
            self.direction = this.sourceStopName + " to " + this.destinationStopName;
        } else {
            self.direction = this.destinationStopName + " to " + this.sourceStopName;
        }
        console.log(this.direction);
    }


    function prepareChartData(chartTitle, chartSeriesData,ytitle) {

        console.log(chartSeriesData);
        	
        var chartData = {
            chart: {
                type: 'scatter',
                zoomType: 'xy'
            },

            title: {
                text: chartTitle
            },

            xAxis: {
                title: {
                    enabled: true,
                    text: 'Time(HH:MM:SS)'
                },
                startOnTick: true,
                endOnTick: true,
                showLastLabel: true
            },
            yAxis: {
                title: {
                    text: ytitle
                },
                categories: []
            },

            plotOptions: {
                scatter: {
                    marker: {
                        radius: 5,
                        states: {
                            hover: {
                                enabled: true,
                                lineColor: 'rgb(100,100,100)'
                            }
                        }
                    },
                    states: {
                        hover: {
                            marker: {
                                enabled: false
                            }
                        }
                    }

                }
            },
            tooltip: {
                formatter: function() {

                    var data = this.series.options.extra[this.series.data.indexOf(this.point)];

                    console.log(this.point);
                    console.log(this.series.data.indexOf(this.point));
                    console.log(data);

                    console.log(data['name']);

                    return '<b> BusStop Name: ' + data.name + '</b>' +
                           '<br />Date: ' + data.date +
                        '<br />Device ID: ' + data.id +
                        '<br />Time: ' + data.time +
                        '<br />latitude: ' + data.lat + ' longitude :' + data.lon;
                }
            },
            exporting: {
                enabled: false
            },
            credits: {
                enabled: false
            },
            series: chartSeriesData
        };

        return chartData;
    }


    //selected busStop
    this.bunchingForSelectedBusStop = function() {
        this.myStyle = {
            "width": "95%",
            "height": "200px",
        };
        var lat = this.selectedBusStop["latitude"];
        var lon = this.selectedBusStop["longitude"];
        var bname = this.selectedBusStop["busStopName"];

        console.log("http://localhost:8080/busbunching/api/GPSData?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date + "&lat=" + lat + "&lon=" + lon + "&name=" + bname);

        $http({
            method: "GET",
            url: "http://localhost:8080/busbunching/api/GPSData/1?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date + "&lat=" + lat + "&lon=" + lon + "&name=" + bname
        }).then(function(resBusData) {
            var data = resBusData.data;
            console.log(data);


            var GPSData = [];
            var tooltipData = [];

            for (count = 0; count < data.length; count++) {
                //var stop = data[count]['busStopName'];

                var g = data[count]['time'];

                var time = g.split(":");
                var actualTime = parseInt(time[0]) + parseInt(time[1]) / 100 + parseInt(time[2]) / 10000;
                var array = [actualTime, 1];
                GPSData.push(array);
                 var d = {
                    "name": data[count]['busStopName'],
                    "id": data[count]['deviceId'],
                    "time": data[count]['time'],
                    "lat": data[count]['latitude'],
                     "date": data[count]['date'],
                    "lon": data[count]['longitude']
                };
                tooltipData.push(d);
            }

            var seriesData = [];

            seriesData.push({
                name: bname,
                color: 'rgba(223, 83, 83, .5)',
                data: GPSData,
                extra: tooltipData
            });

            var chartConfigData = prepareChartData('Bus Bunching Result( Bunching for selected bus Stop )', seriesData,'Bus Stops Number');

            $('#container').empty();
            $('#container').highcharts(chartConfigData);

        })

    };

    //check bunching for whole day
    this.bunchingForRoute = function() {

        this.myStyle = {
            "width": "95%",
            "height": "800px",
        };
        var date = this.date;
        console.log("http://localhost:8080/busbunching/api/GPSData?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date);

        $http({
            method: "GET",
            url: "http://localhost:8080/busbunching/api/GPSData?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date
        }).then(function(resBusData) {
            var data = resBusData.data;

            var header = 'Check bus-bunching for all routes of';
            var GPSData = [];
            var tooltipData = [];
            for (count = 0; count < data.length; count++) {
                var stop = data[count]['busStopID'];

                var g = data[count]['time'];

                var time = g.split(":");
                var actualTime = parseInt(time[0]) + parseInt(time[1]) / 100 + parseInt(time[2]) / 10000;
                var array = [actualTime, stop];
                GPSData.push(array);
                var d = {
                    "name": data[count]['busStopName'],
                    "id": data[count]['deviceId'],
                    "time": data[count]['time'],
                    "lat": data[count]['latitude'],
                     "date": data[count]['date'],
                    "lon": data[count]['longitude']
                };
                tooltipData.push(d);
            }

            var seriesData = [];

            seriesData.push({
                name: date,
                color: 'rgba(223, 83, 83, .5)',
                data: GPSData,
                extra: tooltipData
            });


            var chartConfigData = prepareChartData('Bus Bunching Result( Bunching for all bus Stop )', seriesData,'Bus Stops Number');
            $('#container').empty();
            $('#container').highcharts(chartConfigData);

        })

    };

    //check bunching for one week
    this.bunchingForAllRoute = function() {

        this.myStyle = {
            "width": "95%",
            "height": "800px",
        };

        console.log(this.routeName + "&direction=" + this.direction + "&date=" + this.date + "&start=" + this.startTime + "&end=" + this.endTime);

        $http({
            method: "GET",
            url: "http://localhost:8080/busbunching/api/GPSData?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date + "&start=" + this.startTime + "&end=" + this.endTime
        }).then(function(resBusData) {

            var data = resBusData.data;
            console.log(data);
            var header = 'Check bus-bunching for all routes of';
            var GPSData = [];
            var seriesData = [];
            var tooltipData = [];
            var prevDate;
            var newDate;
            for (count = 0; count < data.length; count++) {
                var stop = data[count]['busStopID'];

                var g = data[count]['time'];

                newDate = data[count]['date'];
                if ((prevDate !== undefined) && (prevDate !== newDate)) {
                    console.log("Number OF days")
                    seriesData.push({
                        name: prevDate,
                        data: GPSData,
                        extra: tooltipData
                    });
                    GPSData = [];
                    tooltipData = [];
                }
                prevDate = newDate;
                var time = g.split(":");
                var actualTime = parseInt(time[0]) + parseInt(time[1]) / 100 + parseInt(time[2]) / 10000;
                var array = [actualTime, stop];
                GPSData.push(array);
                var d = {
                    "name": data[count]['busStopName'],
                    "id": data[count]['deviceId'],
                    "time": data[count]['time'],
                    "lat": data[count]['latitude'],
                     "date": data[count]['date'],
                    "lon": data[count]['longitude']
                };
                tooltipData.push(d);
            }
            seriesData.push({
                name: prevDate,
                data: GPSData,
                extra: tooltipData
            })
            var chartConfigData = prepareChartData('Bus Bunching Result( Bunching for one week(all bus Stop at given time frame) )', seriesData,'Bus Stops Number');
            $('#container').empty();
            $('#container').highcharts(chartConfigData);

        })
    };


    this.bunchingForSelectedBusStopOneWeek = function(){

            this.myStyle = {
                "width": "95%",
                "height": "800px",
            };
            var lat = this.selectedBusStop["latitude"];
            var lon = this.selectedBusStop["longitude"];
            var bname = this.selectedBusStop["busStopName"];

            console.log("http://localhost:8080/busbunching/api/GPSData?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date + "&lat=" + lat + "&lon=" + lon + "&name=" + bname);

            $http({
                method: "GET",
                url: "http://localhost:8080/busbunching/api/GPSData/1?routeNo=" + this.routeName + "&direction=" + this.direction + "&date=" + this.date + "&lat=" + lat + "&lon=" + lon + "&name=" + bname+ "&week=true"
            }).then(function(resBusData) {


                var data = resBusData.data;
                console.log(data);
                var GPSData = [];
                var seriesData = [];
                var tooltipData = [];
                var prevDate;
                var newDate;
                for (count = 0; count < data.length; count++) {
                    var stop = data[count]['busStopID'];

                    var g = data[count]['time'];

                    newDate = data[count]['date'];
                    if ((prevDate !== undefined) && (prevDate !== newDate)) {
                        console.log("Number OF days")
                        seriesData.push({
                            name: prevDate,
                            data: GPSData,
                            extra: tooltipData
                        });
                        GPSData = [];
                        tooltipData = [];
                    }
                    prevDate = newDate;
                    var time = g.split(":");
                    var actualTime = parseInt(time[0]) + parseInt(time[1]) / 100 + parseInt(time[2]) / 10000;
                    var array = [actualTime, stop];
                    GPSData.push(array);
                    var d = {
                        "name": data[count]['busStopName'],
                        "id": data[count]['deviceId'],
                        "time": data[count]['time'],
                        "lat": data[count]['latitude'],
                         "date": data[count]['date'],
                        "lon": data[count]['longitude']
                    };
                    tooltipData.push(d);
                }
                seriesData.push({
                    name: prevDate,
                    data: GPSData,
                    extra: tooltipData
                })
                var chartConfigData = prepareChartData('Bus Bunching Result( Bunching for selected bus Stop-One week data)', seriesData,'One week GPS data for bus stop :'+bname);
                $('#container').empty();
                $('#container').highcharts(chartConfigData);

            

            })

         		
    		
    };
    
 

    this.findBusStops = function() {
        if (this.routeName) {
            $http({
                method: "GET",
                url: "http://localhost:8080/busbunching/api/routeNo/" + this.routeName
            }).then(function(resBusData) {
                self.busStops = resBusData.data;
                console.log(self.busStops);
                var busStop = self.busStops[0];
                self.selectedBusStop = busStop;
                self.sourceStopName = busStop['sourceToDestination'];
                self.destinationStopName = busStop['destinationToSource'];
            })
        }
    };
    this.check = true;
    this.select = function() {
        self.check = false;
        self.name = "Check Bunching for full routes";
        self.sttButton = false;
        self.funName = function() {
            if (self.routeName) {
                $http({
                    method: "GET",
                    url: "http://localhost:8080/busbunching/api/GPSData/routeNo/" + self.routeName + "?Date=12345" + "&StartTime=" + self.startTime + "&EndTime=" + self.endTime
                }).then(function(resBusData) {
                    self.busesData = resBusData.data;
                    self.check = false;
                })
            }
        };
    }
    this.selectBusStop = function(busStop) {

        this.selectedBusStop = busStop.busStopName;
       // this.sourceStopName = busStop.busStopName["busStopName"];
        this.selectedOption = '';
    }
});