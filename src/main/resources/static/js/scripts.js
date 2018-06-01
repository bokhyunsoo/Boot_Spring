$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();
	console.log("click!!");
	
	var queryString = $(".answer-write").serialize();
	console.log("query : " + queryString);
	
	var url = $(".answer-write").attr("action");
	console.log("url :" +url);
	
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
	
}

function onError(){
	
}

function onSuccess(data, status){
	console.log(data);
	var answertemplate = $("#answerTemplate").html();
	var template = answertemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id ,data.id);
	$("#comment").append(template);
	
	$("textarea[name=contents]").val("");
	
}

$("a.link-delete-article").click(deleteAnswer);﻿﻿

function deleteAnswer(e) {
	e.preventDefault();
	
	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("url : " + url);
	
	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function (xhr, status) {
			console.log("error");
		},
		success : function (data, status) {
			console.log(data);
			if (data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
	
}

$("link-modify-article").click(updateAnswer);

function updateAnswer(e) {
	e.preventDefault();
	
	var updateBtn = $(this);
	var url = updateBtn.attr("href");
	console.log("url : " + url);
	
	$.ajax({
		type : 'update',
		url : url,
		dataType : 'json',
		error : function (xhr, status) {
			console.log("error");
		},
		success : function (data, status) {
			console.log(data);
			if (data.valid) {
				
			}
		}
	});
}


String.prototype.format = function() {
	  var args = arguments;
	  return this.replace(/{(\d+)}/g, function(match, number) {
	    return typeof args[number] != 'undefined'
	        ? args[number]
	        : match
	        ;
	  });
	};