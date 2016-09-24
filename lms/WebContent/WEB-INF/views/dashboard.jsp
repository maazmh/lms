<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet"	href="/lms/resources/css/gentelella-custom.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script	src="/lms/resources/js/gentelella-custom.js"></script>

<!-- bootstrap-progressbar -->
<script src="/lms/resources/js/bootstrap-progressbar.js"></script>
<link href="/lms/resources/css/bootstrap-progressbar-3.3.4.css" rel="stylesheet">


<title>Dashboard</title>
<script type="text/javascript">
function init() {
	getData();
	setupNavBar();
}

function setupNavBar() {
	$('#navBarLiDashboard').addClass('active');
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').removeClass('active');
	$('#navBarLiAccount').removeClass('active');
	$('#navBarEmpName').html($('#empNameFromSession').val());
}

function getData() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "${home}api/getLeavesForMonthChart/1/2016",
		//data : JSON.stringify(search),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			displayChartData(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			displayChartData(e);
		},
		done : function(e) {
			console.log("DONE");
			enableSearchButton(true);
		}
	});
	
	function enableSearchButton(flag) {
		alert('enableSearchButton: '+flag);
	}

	function displayChartData(data) {
		//alert('display data' + data);
		var jsonData = JSON.stringify(data);
		//alert('json.stringify: '+ jsonData);
		
		//javascript map for month name
		var mnthNameMap = {};
		mnthNameMap[0] = "Jan";
		mnthNameMap[1] = "Feb";
		mnthNameMap[2] = "Mar";
		mnthNameMap[3] = "Apr";
		mnthNameMap[4] = "May";
		mnthNameMap[5] = "Jun";
		mnthNameMap[6] = "Jul";
		mnthNameMap[7] = "Aug";
		mnthNameMap[8] = "Sep";
		mnthNameMap[9] = "Oct";
		mnthNameMap[10] = "Nov";
		mnthNameMap[11] = "Dec";
		
		//Create an array and push first element in array. First element is the chart column bars names.
		var arrData = [['Year', 'Vacations', 'Sick leave', 'Unpaid leave']];
    	
		/*
			Iterate over a 12 month period. 
			If any month data is found in the ajax call (x == i)? Then go ahead and push the leave details in array.
			If that month data is not found just push [MnthNAme,0,0,0] in the array. 
		*/
    	for(var x=0; x<12; x++) {
    		var monthDataFound = false;
    		for(var i in data) {
    			var mnthObj = data[i];
    			if(x == i) {
    				monthDataFound = true;
    				if (i in mnthNameMap) {
    					var noOfVacDays;
    					var noOfSickDays;
    					var noOfUnpaidDays;
    					for(var j in mnthObj) {
    						if(j=='noOfVacations') {
    							if(mnthObj[j]!=null) {
    								noOfVacDays = mnthObj[j];
    							} else {
    								noOfVacDays = 0;
    							}
    						}
    						if(j=='noOfSickDays') {
    							if(mnthObj[j]!=null) {
    								noOfSickDays = mnthObj[j];
    							} else {
    								noOfSickDays = 0;
    							}
    						}
    						if(j=='noOfUnpaidLeaves') {
    							if(mnthObj[j]!=null) {
    								noOfUnpaidDays = mnthObj[j];
    							} else {
    								noOfUnpaidDays = 0;
    							}
    						}
    					}
    					arrData.push([mnthNameMap[i],noOfVacDays,noOfSickDays,noOfUnpaidDays]);
    				}
    			}
    		}
    		if(!monthDataFound) {
    			arrData.push([mnthNameMap[x],0,0,0]);
    		}
    	}
    	
//     	for(var i=0;i<arrData.length; i++) {
//     		alert(arrData[i]);
//     	}
    	
		
// 		var data = google.visualization.arrayToDataTable([
// 				[ 'Year', 'Vacations', 'Sick leave', 'Unpaid leave' ],
// 				[ 'Jan', 10, 70, 70 ], [ 'Feb', 40, 80, 10 ],
// 				[ 'Mar', 0, 20, 80 ], [ 'Apr', 50, 10, 0 ],
// 				[ 'May', 80, 70, 30 ], [ 'Jun', 10, 0, 50 ],
// 				[ 'Jul', 90, 70, 70 ], [ 'Aug', 40, 50, 10 ],
// 				[ 'Sept', 80, 70, 70 ], [ 'Oct', 40, 50, 10 ],
// 				[ 'Nov', 70, 70, 70 ], [ 'Dec', 30, 10, 50 ] ]);
		
		var data = google.visualization.arrayToDataTable(arrData);
		
		var options = {
			chart : {
				//title: 'Company Performance',
				subtitle : 'Maazs Leave Summary: 2016-2017',
			}
		};
		
		var chart = new google.charts.Bar(document
				.getElementById('columnchart_material'));
		
		chart.draw(data, options);
	}
}
</script>
</head>
<body class="nav-md" onload="init();">
	<%@ include file="navbar.html" %>
	<div style="padding-top: 4em;"></div>
	<input type="hidden" id='leavesChartData' value="${leavesChartData}">
    <div class="container body">
      <div class="main_container">

        <!-- page content -->
        <div class="right_col" role="main">
          <!-- top tiles -->
          <div class="row tile_count">
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i>Leaves Allocated</span>
              <div class="count">2500</div>
              <span class="count_bottom"><i class="green">4% </i> From last Week</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-clock-o"></i> Leaves Taken</span>
              <div class="count">123.50</div>
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>3% </i> From last Week</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Leaves Applied (not approved)</span>
              <div class="count green">2,500</div>
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>34% </i> From last Week</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Leaves Remaining</span>
              <div class="count">4,567</div>
              <span class="count_bottom"><i class="red"><i class="fa fa-sort-desc"></i>12% </i> From last Week</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Unpaid leaves</span>
              <div class="count">2,315</div>
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>34% </i> From last Week</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Leaves Carried forward</span>
              <div class="count">7,325</div>
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>34% </i> From last Week</span>
            </div>
          </div>
          <!-- /top tiles -->

          <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
              <div class="dashboard_graph">

                <div class="row x_title">
                  <div class="col-md-6">
                    <h3>Leaves Summary <small>Per Month</small></h3>
                  </div>
                  <div class="col-md-6">
<!--                     <div id="reportrange" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc"> -->
<!--                       <i class="glyphicon glyphicon-calendar fa fa-calendar"></i> -->
<!--                       <span>December 30, 2014 - January 28, 2015</span> <b class="caret"></b> -->
<!--                     </div> -->
                  </div>
                </div>

                <div>
                  <div style="width: 100%;">
<!--                     <div id="canvas_dahs" class="demo-placeholder" style="width: 100%; height:270px;"></div> -->
<%-- 					<canvas id="myChart" width="400" height="400"></canvas> --%>
					<div id="columnchart_material" style="width: 100%; height: 400px;"></div>
                  </div>
                </div>

                <div class="clearfix"></div>
              </div>
            </div>

          </div>
          <br />

          <div class="row">


            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320">
                <div class="x_title">
                  <h2>App Versions</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Settings 1</a>
                        </li>
                        <li><a href="#">Settings 2</a>
                        </li>
                      </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <h4>App Usage across versions</h4>
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>0.1.5.2</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 66%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>123k</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>

                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>0.1.5.3</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 45%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>53k</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>0.1.5.4</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 25%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>23k</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>0.1.5.5</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 5%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>3k</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="widget_summary">
                    <div class="w_left w_25">
                      <span>0.1.5.6</span>
                    </div>
                    <div class="w_center w_55">
                      <div class="progress">
                        <div class="progress-bar bg-green" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 2%;">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </div>
                    <div class="w_right w_20">
                      <span>1k</span>
                    </div>
                    <div class="clearfix"></div>
                  </div>

                </div>
              </div>
            </div>

            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320 overflow_hidden">
                <div class="x_title">
                  <h2>Device Usage</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Settings 1</a>
                        </li>
                        <li><a href="#">Settings 2</a>
                        </li>
                      </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <table class="" style="width:100%">
                    <tr>
                      <th style="width:37%;">
                        <p>Top 5</p>
                      </th>
                      <th>
                        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                          <p class="">Device</p>
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                          <p class="">Progress</p>
                        </div>
                      </th>
                    </tr>
                    <tr>
                      <td>
                        <canvas id="canvas1" height="140" width="140" style="margin: 15px 10px 10px 0"></canvas>
                      </td>
                      <td>
                        <table class="tile_info">
                          <tr>
                            <td>
                              <p><i class="fa fa-square blue"></i>IOS </p>
                            </td>
                            <td>30%</td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square green"></i>Android </p>
                            </td>
                            <td>10%</td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square purple"></i>Blackberry </p>
                            </td>
                            <td>20%</td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square aero"></i>Symbian </p>
                            </td>
                            <td>15%</td>
                          </tr>
                          <tr>
                            <td>
                              <p><i class="fa fa-square red"></i>Others </p>
                            </td>
                            <td>30%</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </div>
              </div>
            </div>


            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320">
                <div class="x_title">
                  <h2>Quick Settings</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                      <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Settings 1</a>
                        </li>
                        <li><a href="#">Settings 2</a>
                        </li>
                      </ul>
                    </li>
                    <li><a class="close-link"><i class="fa fa-close"></i></a>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <div class="dashboard-widget-content">
                    <ul class="quick-list">
                      <li><i class="fa fa-calendar-o"></i><a href="#">Settings</a>
                      </li>
                      <li><i class="fa fa-bars"></i><a href="#">Subscription</a>
                      </li>
                      <li><i class="fa fa-bar-chart"></i><a href="#">Auto Renewal</a> </li>
                      <li><i class="fa fa-line-chart"></i><a href="#">Achievements</a>
                      </li>
                      <li><i class="fa fa-bar-chart"></i><a href="#">Auto Renewal</a> </li>
                      <li><i class="fa fa-line-chart"></i><a href="#">Achievements</a>
                      </li>
                      <li><i class="fa fa-area-chart"></i><a href="#">Logout</a>
                      </li>
                    </ul>

                    <div class="sidebar-widget">
                      <h4>Profile Completion</h4>
                      <canvas width="150" height="80" id="foo" class="" style="width: 160px; height: 100px;"></canvas>
                      <div class="goal-wrapper">
                        <span class="gauge-value pull-left">$</span>
                        <span id="gauge-text" class="gauge-value pull-left">3,200</span>
                        <span id="goal-text" class="goal-value pull-right">$5,000</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>


        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>
    
    
<!-- Google Charts -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
			google.charts.load('current', {
				'packages' : [ 'bar' ]
			});
			//google.charts.setOnLoadCallback(drawChart);
// 			function drawChart() {
// 				var data = google.visualization.arrayToDataTable([
// 						[ 'Year', 'Vacations', 'Sick leave', 'Unpaid leave' ],
// 						[ 'Jan', 0, 0, 0 ], [ 'Feb', 0, 0, 0 ],
// 						[ 'Mar', 0, 0, 0 ], [ 'Apr', 0, 0, 0 ],
// 						[ 'May', 0, 0, 0 ], [ 'Jun', 0, 0, 0 ],
// 						[ 'Jul', 0, 0, 0 ], [ 'Aug', 0, 0, 0 ],
// 						[ 'Sept', 0, 0, 0 ], [ 'Oct', 0, 0, 0 ],
// 						[ 'Nov', 0, 0, 0 ], [ 'Dec', 0, 0, 0 ] ]);

// 				var options = {
// 					chart : {
// 						//title: 'Company Performance',
// 						subtitle : 'Maazs Leave Summary: 2016-2017',
// 					}
// 				};

// 				var chart = new google.charts.Bar(document
// 						.getElementById('columnchart_material'));

// 				chart.draw(data, options);
// 			}
	</script>
	<!-- Google Charts -->    
     

    <!-- Doughnut Chart -->
    <script>
      $(document).ready(function(){
        var options = {
          legend: false,
          responsive: false
        };
        new Chart(document.getElementById("canvas1"), {
          type: 'doughnut',
          tooltipFillColor: "rgba(51, 51, 51, 0.55)",
          data: {
            labels: [
              "Symbian",
              "Blackberry",
              "Other",
              "Android",
              "IOS"
            ],
            datasets: [{
              data: [15, 20, 30, 10, 30],
              backgroundColor: [
                "#BDC3C7",
                "#9B59B6",
                "#E74C3C",
                "#26B99A",
                "#3498DB"
              ],
              hoverBackgroundColor: [
                "#CFD4D8",
                "#B370CF",
                "#E95E4F",
                "#36CAAB",
                "#49A9EA"
              ]
            }]
          },
          options: options
        });
      });
    </script>
    <!-- /Doughnut Chart -->
  </body>
</html>