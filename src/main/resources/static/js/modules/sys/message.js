$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/message/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true },
			{ label: '标题', name: 'headline', width: 60 },
			{ label: '内容', name: 'content', width: 100 },
            { label: '创建人', name: 'username', width: 100 },
            { label: '创建时间', name: 'create_time', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });



});




var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            headline: null
		},
		showList: true,
		title: null,
        message: {},
        image:'',
        uuid:''
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.message = {};
            vm.uuid=vm.guid();
            vm.message.uuid = vm.uuid;
            new AjaxUpload('#upload', {
                action: baseURL + 'sys/message/upload?token=' + token,
                name: 'file',
                autoSubmit:true,
                responseType:"json",
                data:{"uuid":vm.uuid},
                onSubmit:function(file, extension){

                    if (!(extension && /^(jpg)$/.test(extension.toLowerCase()))){
                        alert('只支持jpg格式的文件！');
                        return false;
                    }
                },
                onComplete : function(file, r){
                    if(r.code == 0){
                        // vm.reload();
                        // console.log(window.location.host+window.location.post)
                        // console.log(baseURL)
                        vm.image="http://"+window.location.host+baseURL+"image/"+r.filename;
                        alert('上传成功');
                        console.log(vm.message.image)
                    }else{
                        alert(r.msg);
                    }
                }
            });
		},
       /* baseUrl: function(){
            //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
            var curWwwPath=window.document.location.href;
            //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
            var pathName=window.document.location.pathname;
            var pos=curWwwPath.indexOf(pathName);
            //获取主机地址，如： http://localhost:8083
            var localhostPaht=curWwwPath.substring(0,pos);
            //获取带"/"的项目名，如：/uimcardprj
            var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
            return(localhostPaht+projectName);
        },*/
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
		/*	$.get(baseURL + "sys/config/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.config = r.config;
            });*/
		},



		del: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/message/delete",
                    contentType: "application/json",
				    data: id,
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
         guid: function(){
  		  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
       	 var r = Math.random()*10|0, v = c == 'x' ? r : (r&0x3|0x8);
      	  return v.toString(10);
  	  });
	},
		saveOrUpdate: function () {
            if(vm.validator()){
				return ;
			}

			var url = vm.message.id == null ? "sys/message/save" : "sys/message/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.message),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'headline': vm.q.headline},
                page:page
            }).trigger("reloadGrid");
		},
		validator: function () {
		/*	if(isBlank(vm.config.key)){
				alert("参数名不能为空");
				return true;
			}

            if(isBlank(vm.config.value)){
                alert("参数值不能为空");
                return true;
            }

            if(isBlank(vm.config.type)){
                alert("参数值不能为空");
                return true;
            }*/
        }
	}
});