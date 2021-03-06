
function stringToDate(strDate) {
	if(strDate!='') {
		var dtParams = strDate.split("-");
		var day = dtParams[0];
		var month = dtParams[1];
		var year = dtParams[2];
		return new Date(year+'-'+month+'-'+day)
	} else return null;
}

function dateDiffBusinessDays(strDtFrom, strDtTo) {
	var date1 = stringToDate(strDtFrom);
	var date2 = stringToDate(strDtTo);
	
	var iWeeks, iDateDiff, iAdjust = 0;
    if (dDate2 < dDate1) return -1; // error code if dates transposed
    var iWeekday1 = dDate1.getDay(); // day of week
    var iWeekday2 = dDate2.getDay();
    iWeekday1 = (iWeekday1 == 0) ? 7 : iWeekday1; // change Sunday from 0 to 7
    iWeekday2 = (iWeekday2 == 0) ? 7 : iWeekday2;
    if ((iWeekday1 > 5) && (iWeekday2 > 5)) iAdjust = 1; // adjustment if both days on weekend
    iWeekday1 = (iWeekday1 > 5) ? 5 : iWeekday1; // only count weekdays
    iWeekday2 = (iWeekday2 > 5) ? 5 : iWeekday2;

    // calculate differnece in weeks (1000mS * 60sec * 60min * 24hrs * 7 days = 604800000)
    iWeeks = Math.floor((dDate2.getTime() - dDate1.getTime()) / 604800000)

    if (iWeekday1 <= iWeekday2) {
      iDateDiff = (iWeeks * 5) + (iWeekday2 - iWeekday1)
    } else {
      iDateDiff = ((iWeeks + 1) * 5) - (iWeekday1 - iWeekday2)
    }

    iDateDiff -= iAdjust // take into account both days on weekend

    return (iDateDiff + 1); // add 1 because dates are inclusive
}

function calcBusinessDays(dtFrom, dtTo) {
	//Function by Maaz 
	//getDay(): Friday is 5 and Saturday is 6. Sunday is 0
	
	if (dtTo < dtFrom) return -1; // error code if dates transposed
	var bussDays=0;
	do {
		if(dtFrom.getDay() != 5 && dtFrom.getDay() != 6) { //Not Friday or Saturday
			bussDays++;
		}
		dtFrom.setDate(dtFrom.getDate() + 1);
	} while(dtFrom.getTime() <= dtTo.getTime());
	return bussDays;
}