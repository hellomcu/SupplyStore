function getGoods(num) {

	/*
	 * var jsonParams = { "page" : num, "num" : 10,
	 *  };
	 */

	$.ajax({
		url : 'front/product/products?page=' + num + '&num=10',
		// contentType : "application/json; charset=utf-8",
		// data : JSON.stringify(jsonParams),
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			// alert(JSON.stringify(data));

//			 alert(JSON.stringify(data.data));

			initData(data.data);

		},
		error : function() {
			alert("异常");
		}
	});

}

function initData(data) {
	for (var i = 0; i < data.length; i++) {
//alert(JSON.stringify(data[i].createTime));
		
		 var x=document.getElementById('myTable').insertRow(i+1)
		  var y=x.insertCell(0)
		  var z=x.insertCell(1)
		  
		   var a=x.insertCell(2)
		  var b=x.insertCell(3)
		  var c=x.insertCell(4)
		  y.innerHTML=data[i].productName
		  z.innerHTML=data[i].productPlace
		  
		  a.innerHTML=data[i].productPrice
		  b.innerHTML=data[i].productUnit
		  
		  c.innerHTML='<input type="number" style="width:60px;"id="in"+i/>&nbsp;&nbsp;<input type="button" value="提交订单" onclick="order('+data[i].id+')"/>'
	}
}

function order(data) {
	alert(data);
}