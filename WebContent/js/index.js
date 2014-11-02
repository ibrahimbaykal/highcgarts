/**
 * 
 */

function onload() {
	// Initiate the chart
	$('#container')
			.highcharts(
					'Map',
					{
						title : {
							text : "Karabük İli Seçim Sonuçları"
						},
						subtitle : {
							text : "İlçeler"
						},
						credits : {
							enabled : false
						},
						tooltip : {
							enabled : false
						},
						legend : {
							enabled : false
						},
						series : [ {
							type : "map",
							dataLabels : {
								enabled : true,
								color : 'white',
								format : '{point.name}'
							},

							name : 'Karabük Oy Dağılımı',
							events : {
								click : function(e) {
									var name = e.point.name;
									getCountyDetail(name);
								}
							},
							point : {
								events : {

								}
							},
							data : [
									{
										name : "Yenice",
										value : 100,
										path : "M0,-375,59,-473,138,-521,270,-557,317,-574,320,-549,295,-504,211,-366,169,-327,197,-268,191,-217,0,-251z"
									},
									{
										name : "Safranbolu",
										value : 25,
										path : "M320,-571,528,-692,565,-743,581,-827,621,-863,669,-880,719,-936,756,-883,711,-832,677,-793,677,-757,694,-717,750,-672,764,-653,767,-619,837,-577,843,-484,806,-442,697,-428,666,-459,567,-481,537,-563,497,-566,452,-560,419,-538,357,-476,295,-490,320,-549z"
									},
									{
										name : "Eflani",
										path : "M719,-934,781,-939,848,-976,874,-959,958,-782,938,-726,865,-672,837,-619,837,-580,775,-625,761,-664,694,-720,683,-757,674,-796,756,-886z"
									},
									{
										name : "Eskipazar",
										path : "M194,-265,191,-144,399,-43,441,-18,604,-167,761,-189,697,-268,685,-243,638,-243,562,-276,452,-254,376,-299,354,-293,292,-251,233,-257z"
									},
									{
										name : "Ovacık",
										path : "M694,-420,694,-262,750,-186,955,-254,1000,-344,890,-467,840,-495,806,-439,716,-434z"
									},
									{
										name : "Karabük Merkez",
										path : "M289,-493,354,-470,427,-549,472,-560,531,-563,565,-476,663,-459,699,-422,697,-259,680,-245,638,-251,576,-282,461,-257,388,-307,351,-293,289,-251,199,-268,166,-335,222,-375z"
									} ]
						} ]
					});
}

function getCountyDetail(name) {
	$.ajax({
		url : "countydetail",
		type:"POST",
		datatype:"JSON",
		data:"op=countydetail&countyID="+name,
		success:successGetCountyDetail
	});
}

function successGetCountyDetail(data){
	var _data = new Array();
	 var limit = data.length;
	 for(var i =0;i<limit;i++){
		 var ero = new Object();
		 ero.name= data[i].electionresult.party;
		 ero.y= data[i].electionresult.vote;
		 _data.push(ero);
	 }
	
	$('#piecontainer').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,//1,
            plotShadow: false
        },
        title: {
            text: 'Siyasi parti Oy Dağılımları'
        },
        subtitle:{
        	text:"Karabük ili genel dağılım"
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        credits:{
        	enabled:false
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Oy Oranı',
            data: _data
        }]
    });
	
}
