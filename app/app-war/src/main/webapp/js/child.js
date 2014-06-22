var Child = {
    // cache and compile template
    taskTemplate: _.template($("#taskItem").html()),
    indicatorTemplate: _.template($("#indicatorItem").html()),
    errorTemplate: $("#error"),

    // REST end point
    endpoint: '/api/child/task',
    endpointNotifications: '/api/notification',
    endpointBlubs: '/api/child/blub',
    notificationsRole: "CHILD",


    init: function () {

        // set event listeners
        $(document).on('click', '.updateState', function (e) {
            e.preventDefault();

            var data = {
                taskId: $(this).attr('data-id'),
                status: $(this).attr('data-state')
            };
            Child.updateTask(data);
        });
        Child.setBuyUrl();
        Child.loadBlubs();

        setTimeout(Child.checkNotifications, 3000);
    },
    setBuyUrl: function () {
        var callbackUrl = "http://" + window.location.host +
            "/html/child/approve.html?code={CODE}";

        var buyUrl = "http://zxing.appspot.com/scan?ret=" + encodeURIComponent(callbackUrl);

        $("#buy").attr('href', buyUrl);
    },

    checkNotifications: function () {
        $.getJSON(Child.endpointNotifications, {'role': Child.notificationsRole},
            function (data) {
                if (data == true) {
                    Child.loadBlubs();
                }
                setTimeout(Child.checkNotifications, 3000);
            });
    },
    loadBlubs: function () {

        $.getJSON(Child.endpointBlubs, function (data) {
            var total = data.nonEarnedBlubs + data.ownedBlubs + data.unconfirmedBlubs;
            $(".ownedBlubs").attr('style', Child.getWidthStyle(total, data.ownedBlubs));
            $(".unconfirmedBlubs").attr('style', Child.getWidthStyle(total, data.unconfirmedBlubs));
            $(".nonEarnedBlubs").attr('style', Child.getWidthStyle(total, data.nonEarnedBlubs));

        });

    },

    getWidthStyle:function(total, val){
        return "width: " + parseInt((val/total)*100) + "%";
    },

    loadTasks: function loadTasks() {

        $.getJSON(Child.endpoint, function (tasks) {
            $(".carousel-inner").html("");
            $("#indicators").html("");

            for (var i in tasks) {

                var task = tasks[i];
                var data = {};
                data.i = i;
                if (i == 0) {
                    task.itemClass = "active";
                    data.class = "active";
                } else {
                    task.itemClass = "";
                    data.class = "";
                }
                var taskDom = Child.taskTemplate({task: task});
                var indicator = Child.indicatorTemplate({data: data});

                $(".carousel-inner").append(taskDom);
                $("#indicators").append(indicator);
            }
        });

    },
    updateTask: function (data) {
        $.ajax({
            url: Child.endpoint,
            data: data,
            type: 'POST',
            success: function(){
                Child.loadTasks();
                Child.loadBlubs();
            },
            error: Child.reqError
        });
    },
    reqError: function (xhr, status, error) {
        Child.errorTemplate.children('span').html(xhr.responseText);
        Child.errorTemplate.show();
    }

};




