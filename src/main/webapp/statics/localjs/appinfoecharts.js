  function chushihua(myChart) {
		var option = {
			title : {
				show : true,
				text : ''
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			series : [ {
				name : '该APP下载数量',
				type : 'pie',
				radius : '90%',
 
				data : [ {
					value : 0,
					name : '无'
				}, ]
			} ]
		};
 
		myChart.setOption(option);
	}
 
	//从数据库读取数据赋值给echarts
	function fuzhi(myChart) {
		$.ajax({
			type : "GET",
			url : "echartsData",
			dataType : "json",
			success : function(data) {
				//创建一个数组，用来装对象传给series.data，因为series.data里面不能直接for循环
				var servicedata = [];
				console.log(data);
				if(data.length>0){
				for (var i = 0; i < data.length; i++) {
					var obj = new Object();
					obj.name = data[i].softwareName;
					obj.value = data[i].downloads;
					servicedata[i] = obj;
				}
				myChart.setOption({
					title : {
						text : ''
					},
					series : [ {
						name : '下载次数',
						type : 'pie',
						radius : '60%',
						data : servicedata,
					} ]
 
				});
				}else{
					alert("该APP还没有数据");
				}
			}
		});
	}
 
	//初始化echarts实例
	var myChart = echarts.init(document.getElementById('chartmain'));
	chushihua(myChart);
	fuzhi(myChart);

