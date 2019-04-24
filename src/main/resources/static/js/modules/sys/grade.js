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
        showUserList: false,
        showAddUpdate: false,
        showNoGradeUser: false,
        id:'',
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
        queryUser: function () {
            vm.reloadUserList();

        },
		add: function(){
            vm.showAddUpdate = true;
			vm.showList = false;
            vm.showUserList = false;
            vm.showNoGradeUser = false;
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
        //显示班级信息
        showGrade: function(){
            //获取id
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.id=id;
            vm.showList = false;
            vm.showUserList = true;
            vm.showAddUpdate = false;
            vm.showNoGradeUser = false;
            $("#jqGrid1").jqGrid({
                url: baseURL + 'sys/grade/userList/'+id,
                datatype: "json",
                colModel: [
                    { label: 'ID', name: 'userId', width: 30, key: true },
                    { label: '姓名', name: 'username', width: 60 },
                    { label: '性别', name: 'sex', width: 100, formatter: function(value, options, row){
                            return value === 0 ?
                                '<span class="label label-danger">男</span>' :
                                '<span class="label label-success">女</span>';
                        }},
                    { label: '年龄', name: 'age', width: 100 },
                    { label: '角色', name: 'roleName', width: 100 }
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
            }).trigger("reloadGrid");
        },
        //显示用户信息
        showUser: function(){
            vm.showList = false;
            vm.showUserList = false;
            vm.showAddUpdate = false;
            vm.showNoGradeUser = true;

            $("#jqGrid2").jqGrid({
                url: baseURL + 'sys/grade/userList',
                datatype: "json",
                colModel: [
                    { label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
                    { label: '用户名', name: 'username', width: 75 },
                    { label: '性别', name: 'sex', width: 25, formatter: function(value, options, row){
                            return value === 0 ?
                                '<span class="label label-danger">男</span>' :
                                '<span class="label label-success">女</span>';
                        }},
                    { label: '年龄', name: 'age', width: 25 },
                    { label: '邮箱', name: 'email', width: 90 },
                    { label: '手机号', name: 'mobile', width: 50 },
                    { label: '角色', name: 'roleName', width: 55 },
                    { label: '状态', name: 'status', width: 25, formatter: function(value, options, row){
                            return value === 0 ?
                                '<span class="label label-danger">禁用</span>' :
                                '<span class="label label-success">正常</span>';
                        }},
                    { label: '创建时间', name: 'createTime', index: "create_time", width: 80}
                ],
                viewrecords: true,
                height: 385,
                autowidth: true,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                multiselect: true,
                pager: "#jqGridPager2",
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
                    $("#jqGrid2").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            })

            $("#gbox_jqGrid2").css('width',"1280px")
            $("#gbox_jqGrid2 table").css('width',"1280px")
           // 1280px $("#jqGrid2").jqGrid('setGridParam',{
           //  }).trigger("reloadGrid");
           //  $("#jqGrid2 tr ").css('width',"1280px")
           //  console.log($(window).width()-300)
           //  console.log($("#jqGrid2").css('width'))
        },
        addGradeUser: function () {
            var userIds = getSelectedRows2();
            if(userIds == null){
                return ;
            }
            var params ={
                id:vm.id,
                userIds:userIds
            }
        console.log(userIds);
            $.ajax({
                type: "POST",
                url: baseURL + "sys/grade/updateGradeUser/",
                data: JSON.stringify(params),
                dataType:'JSON',
                contentType: "application/json",
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reloadUserList();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        //去除成员
        removeGradeUser: function () {
            var userIds = getSelectedRows1();
            if(userIds == null){
                return ;
            }
            $.ajax({
                type: "POST",
                url: baseURL + "sys/grade/removeGradeUser",
                data: JSON.stringify(userIds),
                dataType:'JSON',
                contentType: "application/json",
                success: function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reloadUserList();
                        });
                    }else{
                        alert(r.msg);
                    }
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
                vm.showUserList = false;
                vm.showAddUpdate = true;
                vm.showNoGradeUser = false;
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
            vm.showUserList = false;
            vm.showAddUpdate = false;
            vm.showNoGradeUser = false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'areaName': vm.q.areaName},
                page:page
            }).trigger("reloadGrid");
		},
        reloadUserList: function () {
            vm.showList = false;
            vm.showUserList = true;
            vm.showAddUpdate = false;
            vm.showNoGradeUser = false;
            var page = $("#jqGrid1").jqGrid('getGridParam','page');
            $("#jqGrid1").jqGrid('setGridParam',{
                postData:{'userName': vm.q.userName},
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