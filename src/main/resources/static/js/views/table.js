const vm = new Vue({
    el: '#app',
    data: {
        //加载特效
        listLoading: false,

        //加载按钮
        loading: false,

        //列表数据
        userList: [],
        //分页
        currentPage: 1,
        totalPage: 0,
        pageSizes: [2, 5, 20, 50],
        pageSize: 5,
        total: 0,

        // 模板数据
        tempTableData: [],

        //多选
        multipleSelection: [],

        //Excel文件选择
        selectFileFlag: '',
        file: '',

        //系统用户名
        username: localStorage.getItem("username"),

        //查询条件
        username1: '',

        //用于弹出(true)或关闭(false)弹框
        dialogFormVisible: false,
        dialogFormVisible2: false,
        //表单数据
        user: {
            id: 0,
            username: '',
            password: '',
            createDate: new Date().getFullYear() + "-" + format2(new Date().getMonth() + 1) + "-" + format2(new Date().getDate()),
            remark: ''
        },
        formLabelWidth: '100px',
        //表单验证
        rules: {
            username:
                [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                ],
            password:
                [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
                ],
        },


        //日期显示
        createDateOptions: {},

        //ueditor
        editor: null,
        editor2: null,

    },
    mounted: function () {
        //设置日期显示规则
        this.createDateOptions = {
            disabledDate: function (time) {
                let currentTime = new Date();
                return time.getTime() <= currentTime.getTime();
            }
        };


        this.getUserList(this.currentPage);

        // 实例化editor编辑器
        // UE.getEditor('editor');
    },
    watch: {
        'user.username':
            {
                handler: function (val, oldValue) {
                    let _this = this;
                    if (val == null || val.length > 8) {
                        Vue.nextTick(function () {
                            _this.user.username = oldValue;
                        })
                    }
                },
                deep: true
            }
    },
    destroyed: function () {
        this.editor.destroy();
        // this.editor2.destroy();
    },
    methods: {
        //日期实时改变
        createDateChange: function (val) {
            vm.user.createDate = val;
        },

        /**
         * 格式化日期
         * @param time
         * @return {string}
         */
        formatDate: function (time) {
            const date = new Date(time.toString().replace(/-/g, "/"));
            const seperator1 = "-";
            let month = date.getMonth() + 1;
            let strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            return date.getFullYear() + seperator1 + month + seperator1 + strDate;
        },

        /*
         * 初始化权限
         */
        init: function () {
            vm.loading = true;
            axios({
                method: 'get',
                url: 'reload',
                data: {},
                dataType: 'json'
            }).then((response) => {
                if (response.data.code === 0) {
                    vm.open("初始化成功！", "success");
                    vm.loading = false;
                } else {
                    vm.open("初始化失败！", "error");
                    vm.loading = false;
                }
            });
        },

        /*
         * 表格隔行变色
         */
        tableRowClassName: function ({row, rowIndex}) {
            if (rowIndex % 2 === 1) {
                return 'success-row';
            }
            return '';
        },

        //修改重置表单
        updateResetForm: function (formName) {
            vm.dialogFormVisible2 = false;
            // this.editor2.setContent('');
            this.$refs[formName].resetFields();
            // this.editor2.destroy();
        },
        //提交表单,成功则执行isFunction()方法
        submitForm: function (formName, isFunction) {
            this.$refs[formName].validate(function (valid) {
                if (valid) {
                    if ((typeof isFunction) === 'function') {
                        isFunction();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        //保存重置表单
        resetForm: function (formName) {
            vm.dialogFormVisible = false;
            this.editor.setContent('');
            this.$refs[formName].resetFields();
            // this.editor.destroy();
        },


        /*
         * 处理当页容量和当前页码
         */
        handleSizeChange: function (val) {
            if (vm.total > 0) {
                console.log(`每页 ${val} 条`);
                vm.pageSize = val;
                vm.getUserList(vm.currentPage);
            }
        },
        handleCurrentChange: function (currentPage) {
            if (vm.total > 0) {
                console.log(`当前页: ${currentPage}`);
                vm.currentPage = currentPage;
                vm.getUserList(currentPage);
            }
        },

        /**
         * 查询分页用户列表
         *
         * @param pageNo
         */
        getUserList: function (pageNo) {
            //显示加载
            this.listLoading = true;
            if (!isNaN(pageNo)) {
                this.currentPage = pageNo;
            }

            //ajax方式与后台交互
            /*$.ajax({
                type: 'get',
                url: 'userList/' + pageNo + '/' + vm.pageSize,
                data: {
                    username: vm.username1
                },
                dataType: 'json',
                success: function (data) {
                    vm.currentPage = data.pageNo;
                    // vm.pageSize = data.pageSize;
                    vm.total = data.totalCount;
                    vm.userList = data.userList;
                }
            });*/


            //使用vue-resource插件与后台交互，全版
            /*vm.$http({
                method: 'GET',
                url: 'userList/' + pageNo + '/' + vm.pageSize + '?username=' + vm.username1,
                data: {}, //get请求必须在路径后加数据
                dataType: 'json'
            }).then(function (response) {
                vm.currentPage = response.data.pageNo;
                vm.total = response.data.totalCount;
                vm.userList = response.data.userList;
            }).catch(function (error) {

            });*/
            //简写版get方式
            /*vm.$http.get(
                'userList/' + pageNo + '/' + vm.pageSize + '?username=' + vm.username1,
            ).then(function (data) {
                //.data不可去
                vm.currentPage = data.data.pageNo;
                vm.total = data.data.totalCount;
                vm.userList = data.data.userList;
            });*/

            // 使用axios方式与后台交互，全版
            axios({
                method: 'get',
                url: 'userList/' + pageNo + '/' + this.pageSize + '?username=' + this.username1,
                data: {},
                dataType: 'json'
            }).then(function (response) {
                const res = response.data;
                //.data不可去
                vm.currentPage = res.pageNo;
                vm.totalPage = res.totalPage;
                vm.total = res.totalCount;
                vm.userList = res.userList;
                console.log(vm.total);
                //关闭加载显示
                vm.listLoading = false;
            }).catch(function (error) {
                vm.listLoading = false;
            });
            // 简写版
            /*axios.get('userList/' + pageNo + '/' + vm.pageSize + '?username=' + vm.username1,
                {}).then(function (response) {
                vm.currentPage = response.data.pageNo;
                vm.total = response.data.totalCount;
                vm.userList = response.data.userList;
            })*/


        },

        toAdd: function () {
            this.user.username = '';
            this.user.password = '';

            this.editor = UE.getEditor('editor');
            //打开保存弹窗
            vm.dialogFormVisible = true;
        },
        /**
         *  保存用户
         */
        addUser: function () {
            // vm.submitForm('user');

            let remark = this.editor.getContent();
            console.log(remark);

            const data = {
                username: vm.user.username,
                password: vm.user.password,
                createDate: vm.user.createDate,
                remark: remark
            };
            axios({
                method: 'post',
                url: 'user/add',
                headers: {
                    'Content-type': 'application/json'
                },
                data: JSON.stringify(data),
                dataType: 'text',
            }).then(function (response) {
                //.data不可去
                if (response.data === 'suc') {
                    //关闭弹窗
                    vm.dialogFormVisible = false;

                    vm.open('保存成功！', 'success');
                    vm.editor.setContent("");
                    vm.getUserList(vm.currentPage)
                } else {
                    vm.open('保存失败, 用户名已存在！', 'error')
                }
            }).catch(function () {
                vm.open('保存失败！', 'error')
            })
        },


        onChangeFile: function (event) {
            const str = $("#file").val();
            const index = str.lastIndexOf('.');
            let photoExt = str.substr(index, 4);
            photoExt = photoExt.toLowerCase();
            if (photoExt !== '' && !(photoExt === '.xls' || photoExt === '.xlsx')) {
                // this.$alert("请上传excel文件格式!");
                // 此alert方法已经在common.js文件中重写过
                alert("请上传excel文件格式!");
                this.isNeedFileExtension = false;
                return false;
            } else {
                this.selectFileFlag = '';
                this.file = '';
                let meFile = event.target.files[0];
                if (event !== undefined && meFile != null && meFile !== '') {
                    this.file = event.target.files[0];
                    this.isNeedFileExtension = true;
                    //截取名称最后18位
                    this.selectFileFlag = event.target.files[0].name;
                }
            }
        },
        showSelectFileWin: function () {
            this.selectFileFlag = '';
            this.file = '';
            $("#upload_form")[0].reset();
            $("#file").click();
        },
        exportData: function () {
            document.getElementById("ifile").src = "export/UserExcelTemplate";
        },
        uploadFile: function (event) {
            if (!this.isNeedFileExtension) {
                alert("请上传excel文件格式!");
            } else {
                event.preventDefault();
                if (this.file === '' || this.file === undefined) {
                    alert("请选择excel文件!");
                    return;
                }
                const formData = new FormData();
                formData.append('file', this.file);
                const config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                };
                this.tempTableData = this.userList;
                this.userList = [];
                this.loading = true;
                let flag = false;
                let hh;
                const url = "import/importUserExcel";
                axios.post(url, formData, config).then(function (response) {
                    flag = true;
                    if (response.data.code !== undefined && response.data.code === 401) {
                        return;
                    }
                    vm.selectFileFlag = '';
                    vm.file = '';
                    if (response.data.errorCount > 0) {
                        vm.userList = vm.tempTableData;
                        alert("请检查数据是否完整！");
                        vm.loading = false;
                        return;
                    }
                    if (response.data.success) {
                        if (vm.tempTableData.length + response.data.reason.length + response.data.errorCount + response.data.repeatCount > 500) {
                            vm.userList = vm.tempTableData;
                            alert('导入数据超过500条，请修改模板！');
                            return;
                        }
                        let str = "";
                        let str2 = "";
                        if (response.data.repeatEntityList.length > 0) {

                            for (let i = 0; i < response.data.repeatEntityList.length; i++) {
                                str = str + response.data.repeatEntityList[i].username + "、";
                            }

                        }
                        if (response.data.errorEntityList.length > 0) {

                            for (let i = 0; i < response.data.errorEntityList.length; i++) {
                                str2 = str2 + response.data.errorEntityList[i].username + "、";
                            }
                        }
                        if (str === '') {
                            str = "无数据"
                        } else {
                            str = vm.formatStr(str);
                        }
                        if (str2 === '') {
                            str2 = "无数据"
                        } else {
                            str2 = vm.formatStr(str2);
                        }

                        alert("共计准备导入" + response.data.importTotalCount + "条，成功" + response.data.reason.length + "条；\n库里已存在" +
                            response.data.repeatCount + "条，对应的用户名：" + str + "；\n错误数据" +
                            response.data.errorEntityList.length + "条，对应用户名: " + str2 + "。");
                        vm.getUserList(1);
                    } else {
                        vm.userList = vm.tempTableData;
                        alert(response.data.reason);
                    }
                    vm.loading = false;
                }, (err) => {
                    vm.loading = false;
                    if (err.status === 408) {
                        alert("导入出错!");
                    }
                });
                let intervelId = setInterval(function () {
                    if (flag) {
                        hh = $(document).height();
                        $("body", parent.document).find("#myiframe").css('height', hh + 'px');
                        clearInterval(intervelId);
                        return;
                    }
                }, 50);
            }
        },
        formatStr: function (str) {
            return str.substring(0, str.lastIndexOf('、'))
        },

        //日期格式
        dateFormat: function (row, column, cellValue, index) {
            if (cellValue == null) {
                return '—— ——';
            }
            return cellValue.substring(0, 10);
        },

        //数字格式
        numberFormat: function (row, column, cellValue) {
            var number = cellValue;
            var decimals = 2;
            var dec_point = ".";
            var thousands_sep = ",";
            number = (number + '').replace(/[^0-9+-Ee.]/g, '');
            var n = !isFinite(+number) ? 0 : +number,
                prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
                sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
                dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
                s = '',
                toFixedFix = function (n, prec) {
                    var k = Math.pow(10, prec);
                    return '' + Math.round(n * k) / k;
                };

            s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
            var re = /(-?\d+)(\d{3})/;
            while (re.test(s[0])) {
                s[0] = s[0].replace(re, "$1" + sep + "$2");
            }

            if ((s[1] || '').length < prec) {
                s[1] = s[1] || '';
                s[1] += new Array(prec - s[1].length + 1).join('0');
            }
            return s.join(dec);
        },
        //给表格设置默认值
        tableDataFormat: function (row, column, cellValue) {
            if (cellValue == null) {
                return '—— ——';
            }
            return cellValue;
        },

        /**
         * 导出Excel文件
         */
        exportUserExcel: function () {
            location.href = "export/userExcel"
        },


        /**
         * 去修改页面
         * @param index
         * @param row
         */
        handleEdit: function (index, row) {
            console.log(index, row);

            // this.editor2 = UE.getEditor('editor2');

            //打开修改弹窗
            /*vm.dialogFormVisible2 = true;*/

            //给修改表单赋值
            /*this.user.id = vm.userList[index].id;
            this.user.username = vm.userList[index].username;
            this.user.password = vm.userList[index].password;
            this.user.createDate = vm.userList[index].createDate;
            this.user.remark = vm.userList[index].remark;*/
            // this.editor2.setContent(vm.userList[index].remark)

            location.href = 'toUpdate?username=' + vm.userList[index].username + '&pageNo=' + vm.currentPage + '&id=' + vm.userList[index].id;
        },

        /**
         * 修改
         */
        updateUser: function () {
            // let remark = this.editor2.getContent();

            const data = {
                id: vm.user.id,
                username: vm.user.username,
                password: vm.user.password,
                createDate: vm.user.createDate,
                // remark: remark
            };

            axios({
                method: 'put',
                url: 'user/update',
                headers: {
                    'Content-type': 'application/json'
                },
                data: JSON.stringify(data),
                dataType: 'text',
            }).then(function (response) {
                //.data不可去
                if (response.data === 'suc') {
                    vm.dialogFormVisible2 = false;
                    vm.open('修改成功！', 'success');
                    vm.getUserList(vm.currentPage)
                } else {
                    vm.open('修改失败！', 'error')
                }
            })
        },

        //提示警告是否删除
        open2: function (index, row) {
            /*this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                //删除数据
                vm.handleDelete(index, row);
            }).catch(function () {
                this.$message({
                    showClose: true,
                    type: 'info',
                    message: '已取消删除'
                });
            });*/

            confirm('确定要删除吗?', function () {
                axios({
                    //delete请求方式需要在url路径后传参
                    method: 'delete',
                    url: 'user/delete?id=' + vm.userList[index].id,
                    data: {},
                    dataType: 'text',
                }).then((response) => {
                    //.data不可去
                    if (response.data === 'suc') {
                        // vm.open("删除成功！", "success");
                        alert("删除成功!");
                        // layer.msg("删除成功!", {icon: 1});
                        vm.getUserList(vm.currentPage)
                    } else {
                        // vm.open('删除失败！', 'error')
                        // layer.msg("删除失败!", {icon: 1});
                        alert("删除失败!");
                    }
                })
            })

        },
        /**
         * 删除
         * @param index
         * @param row
         */
        handleDelete: function (index, row) {
            console.log(index, row);
            axios({
                //delete请求方式需要在url路径后传参
                method: 'delete',
                url: 'user/delete?id=' + vm.userList[index].id,
                data: {},
                dataType: 'text',
            }).then((response) => {
                //.data不可去
                if (response.data === 'suc') {
                    // vm.open("删除成功！", "success");
                    layer.msg("删除成功!", {icon: 1});
                    vm.getUserList(vm.currentPage)
                } else {
                    // vm.open('删除失败！', 'error')
                    layer.msg("删除失败!", {icon: 1});
                }
            })

        },
        //处理多选框改变
        handleSelectionChange: function (val) {
            vm.multipleSelection = val;
        },
        //获取删除数据的所有id
        getDeleteIds: function () {
            let ids = [];
            for (let i = 0; i < this.multipleSelection.length; i++) {
                ids.push(this.multipleSelection[i].id);
            }
            return ids;
        },
        //批量删除
        batchRemove: function () {
            if (this.multipleSelection.length === 0) {
                alert('请先选择要删除的数据!');
                return;
            }

            this.$confirm('此操作将永久删除这些数据, 是否继续?', '提示', {
                // confirm('此操作将永久删除这些数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                //获取要删除的id
                const ids = vm.getDeleteIds();
                //批量删除数据
                axios({
                    method: 'post',
                    url: 'user/batchRemove',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    data: JSON.stringify(ids),
                    dataType: 'text',
                }).then(function (response) {
                    //.data不可去
                    if (response.data === 'suc') {
                        vm.open("删除成功！", "success");
                        vm.getUserList(vm.currentPage)
                    } else {
                        vm.open('删除失败！', 'error')
                    }
                });
            }).catch(function () {
                this.$message({
                    showClose: true,
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

        /**
         * 行号
         */
        mainIndex: function (index) {
            return index + (this.currentPage - 1) * this.pageSize + 1;
        },

        //提示窗
        open: function (message, type) {
            this.$message({
                showClose: true,
                message: message,
                type: type
            });
        },

    },
});

function format2(value) {
    if (value < 10) {
        return "0" + value;
    } else {
        return value;
    }
}

