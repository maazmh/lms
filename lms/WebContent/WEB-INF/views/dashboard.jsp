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
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script	src="/lms/resources/js/gentelella-custom.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>

<!-- bootstrap-progressbar -->
<script src="/lms/resources/js/bootstrap-progressbar.js"></script>
<link href="/lms/resources/css/bootstrap-progressbar-3.3.4.css" rel="stylesheet">


<title>Dashboard</title>
<script type="text/javascript">
function init() {
	getData();
	setupNavBar();
	//setDataForPieChart();
}

function setupNavBar() {
	$('#navBarLiDashboard').addClass('active');
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').removeClass('active');
	$('#navBarLiAccount').removeClass('active');
	$('#navBarEmpName').html($('#empNameFromSession').val());
	$('#navBarLiReport').removeClass('active');
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


function validateAndSave() {
	if($('#dtFrom').val()=='' || $('#dtTo').val()=='') {
		var errMsg = '<ul><li>Dates cannot be empty</li></ul>';	
		$('#errorMsgModal').html(errMsg);
		return false;
	} else {
		var strFrom = $('#dtFrom').val();
		var strTo = $('#dtTo').val();
		
		$('#spanDtFrom').html($('#dtFrom').val());
		$('#spanDtTo').html($('#dtTo').val());
		$('#spanLeaveType').html($('#leaveType option:selected').text());
		$('#spanAppliedDays').html($('#dtTo').val());
		$('#spanRemainingLeaves').html($('#leaveType').val());
		
		alert('chk');
		
		var dtToArray = $('#dtTo').val().split('-');
		var dtToMonth = dtToArray[1];
		var dtToDate = dtToArray[0];
		var dtToYear = dtToArray[2];
		//$('#dtTo').val(dtToDate + '-' + dtToMonth + '-' + dtToYear);
		
		var dtFromArray = $('#dtFrom').val().split('-');
		var dtFromMonth = dtFromArray[1];
		var dtFromDate = dtFromArray[0];
		var dtFromYear = dtFromArray[2];
		//$('#dtFrom').val(dtFromDate + '-' + dtFromMonth + '-' + dtFromYear);
		

		var jsDtFrom = new Date(dtFromYear+'-'+dtFromMonth+'-'+dtFromDate);
		var jsDtTo = new Date(dtToYear+'-'+dtToMonth+'-'+dtToDate);
		
// 		alert('from: '+dtFromYear+'-'+dtFromMonth+'-'+dtFromDate);
// 		alert('to: '+dtToYear+'-'+dtToMonth+'-'+dtToDate);
		
// 		alert('from Day: '+jsDtFrom.getDay());
// 		alert('to day: '+jsDtTo.getDay());
		
		alert('before saving check for dates. Leave should not start or end on friday. This is mondia media policy. Different companies may have different policies.');
		
		document.getElementById("btnSaveChanges").disabled = true;
		var form = document.getElementById('leavesForm');
		form.setAttribute("method", 'post');
	    form.setAttribute("action", 'saveLeave');
		form.submit();
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
              <div class="count green"><c:out value="${leavesForm.leavesAllocated+leavesForm.carriedForwardLeaves}" /></div>
              <span class="count_bottom"><i class="green"><c:out value="${leavesForm.carriedForwardLeaves}" /> </i> Carried From last Year</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-clock-o"></i> Leaves Taken</span>
              <div class="count"><c:out value="${leavesForm.leavesUsed}" /></div>
<!--               <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>3% </i> From last Week</span> -->
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Sick Leaves Taken</span>
              <div class="count"><c:out value="${leavesForm.sickLeavesUsed}" /></div>
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>9</i> Days Allocated per year</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Leaves Applied (not approved)</span>
              <div class="count"><c:out value="${leavesForm.leavesPendingApproval}" /></div>
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i> </i> Waiting for Approval</span>
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Leaves Remaining</span>
              <div class="count green"><c:out value="${leavesForm.leavesRemaining}" /></div>
<!--               <span class="count_bottom"><i class="red"><i class="fa fa-sort-desc"></i>12% </i> From last Week</span> -->
            </div>
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Unpaid leaves</span>
              <div class="count"><c:out value="${leavesForm.unpaidLeavesUsed}" /></div>
<!--               <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i>34% </i> From last Week</span> -->
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
                  <h2>Staff Details</h2>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                  <form class="form-horizontal">
	                  <div class="form-group">
					    <label>Name		: </label>
					    <c:out value="${leavesForm.employeeName}"></c:out>
					  </div>
					  <div class="form-group">
					    <label>Department	:</label>
					    <c:out value="${leavesForm.department}"></c:out>
					  </div>
					  <div class="form-group">
					    <label>Approvers: </label>
<%-- 					    <c:out value="${leavesForm.department}"></c:out> --%>
					  </div>
					  <div class="form-group">
					    <label>Next Leave: </label>
<%-- 					    <c:out value="${leavesForm.department}"></c:out> --%>
							(From Date) to (To Date)
					  </div>
				  </form>
                </div>
              </div>
            </div>

            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320 overflow_hidden">
                <div class="x_title">
                  <h2>Quick Apply Leave</h2>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                	<form:form method="post" action="quickSaveLeave" modelAttribute="dashboardForm" commandName="dashboardForm" cssClass="form-signin">
						<form:hidden path="companyAccountId"/>
						<form:hidden path="employeeId"/>
						<form:hidden path="leavesAllocated"/>
						<form:hidden path="leavesUsed"/>
						<form:hidden path="leavesRemaining"/>
						<form:hidden path="sickLeavesUsed"/>
						<form:hidden path="unpaidLeavesUsed"/>
						<form:hidden path="leavesPendingApproval"/>
						<form:hidden path="year"/>
						<form:hidden path="fiscalYearId"/>
						<p id="errorMsgModal" class="bg-danger"></p>
				      	<div class="form-group">
				      		​​<label for="leaveType">Leaves Type</label>
							<form:select path="leaveType" cssClass="form-control">
								<form:options items="${leaveTypes}" />
							</form:select>
							​​<label for="leaveReason">Leaves Reason</label>
							<form:input path="leaveReason" cssClass="form-control" />
				      		<label>Select Dates</label>
							<div class="input-group input-daterange" data-provide="datepicker" data-date-format="dd-mm-yyyy">
							    <form:input path="dtFrom" cssClass="form-control"  placeholder="Leave From" />
							    <span class="input-group-addon">to</span>
							    <form:input path="dtTo" cssClass="form-control"  placeholder="Leave To" />
							</div>
				      	</div>
				      	<button id="btnValidate" type="button" class="btn btn-primary" onclick="validateAndSave();">Apply</button>
				      </form:form>
                </div>
              </div>
            </div>


            <div class="col-md-4 col-sm-4 col-xs-12">
              <div class="x_panel tile fixed_height_320">
                <div class="x_title">
                  <h2>Vacation Leaves</h2>
                  <div class="clearfix"></div>
                </div>
                <div class="x_content">
                	Pie chart or a doughnut chart showing vacation leaves applied vs remaining 
                	<div id="piechart" style="width: 500px; height: 500px;"></div>
                </div>
              </div>
            </div>

          </div>


        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Leave Management System By <a href="#">Maaz Hurzuk</a>
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
				'packages' : [ 'bar', 'pie' ]
			});
			
			google.charts.load('current', {'packages':['corechart']});
    </script>  
     

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