var chartDataStr = decodeHtml(chartData);
chartJSONArray = JSON.parse(chartDataStr);

var arrayLength = chartJSONArray.length;
var numericData = [];
var labelData = [];

for(var i =0;i<arrayLength;i++)
{
	numericData[i]=chartJSONArray[i].value;
	labelData[i]=chartJSONArray[i].label;
}

// For a pie chart
new Chart(document.getElementById("myPieChart"), {
    type: 'pie',
    // The data for our dataset
    data: {
        labels: labelData,
        datasets: [{
            label: 'My First dataset',
            backgroundColor: ["#3e95cd","#8e5ea2","#3cba9f"],
            data: numericData
        }]
    },

    // Configuration options go here
    options: {
	title:
	{
		display:true,
		text: 'Project Status'
	}
}
});

function decodeHtml(html)
{
	var txt=document.createElement("textarea");
	txt.innerHTML=html;
	return txt.value;
}