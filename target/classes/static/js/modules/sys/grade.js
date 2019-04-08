$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/grade/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true },
			{ label: '校区名称', name: 'areaName', width: 60 },
			{ label: '学校名称', name: 'schoolName', width: 100 },
            { label: '年级', name: 'gradeName', width: 100 },
            { label: '班级', name: 'className', width: 100 }
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
			key: null
		},
		showList: true,
		title: null,
        campusList:{},
        schoolList:{},
        gradeList:{},
        classList:{},
		grade: {
            areaId:[0],
            schoolId:[0],
            gradeId:[0],
            classId:[0]
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
            vm.campusList = {};
            vm.schoolList = {};
            vm.gradeList = {};
            vm.classList = {};
            vm.grade={};
            //获取校区信息
            this.getCampusList("campusType");
            this.getSchoolList("schoolName");
            this.getGradeList("gradeName");
            this.getClassList("className");
		},
        showGrade: function(){
            vm.showList = false;
            //获取id
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            //获取该信息
            $.get(baseURL + "sys/grade/info/"+id, function(r){
                vm.grade = r.grade;
                console.log(vm.grade);
            });
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.grade),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert('操作失败');
                    }
                }
            });
            $("#jqGrid1").jqGrid({
                url: baseURL + 'sys/grade/list',
                datatype: "json",
                colModel: [
                    { label: 'ID', name: 'id', width: 30, key: true },
                    { label: '姓名', name: 'areaName', width: 60 },
                    { label: '性别', name: 'schoolName', width: 100 },
                    { label: '年龄', name: 'gradeName', width: 100 },
                    { label: '角色', name: 'className', width: 100 }
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:true,
                multiselect: true,
                pager: "#jqGridPager1",
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
                    $("#jqGrid1").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
        },
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			$.get(baseURL + "sys/grade/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.grade = r.grade;
            });
		},
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/grade/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		saveOrUpdate: function () {
            if(vm.validator()){
				return ;
			}

			var url = vm.grade.id == null ? "sys/grade/save" : "sys/grade/update";
            console.log(vm.grade);
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.grade),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert('操作失败');
					}
				}
			});
		},
        getCampusList: function(type){
            $.get(baseURL + "sys/config/pull/"+type, function(r){
                vm.campusList = r.list;
            });
        },
        getSchoolList: function(type){
            $.get(baseURL + "sys/config/pull/"+type, function(r){
                vm.schoolList = r.list;
            });
        },
        getGradeList: function(type){
            $.get(baseURL + "sys/config/pull/"+type, function(r){
                vm.gradeList = r.list;
            });
        },
        getClassList: function(type){
            $.get(baseURL + "sys/config/pull/"+type, function(r){
                vm.classList = r.list;
            });
        },
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'areaName': vm.q.areaName},
                page:page
            }).trigger("reloadGrid");
		},

		validator: function () {
			/*if(isBlank(vm.config.key)){
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