var Parent = {

    // cache and compile template
    taskTemplate: _.template($("#taskRow").html()),
    errorTemplate: $("#error"),

    // REST end point
    endpoint: '/api/parent/task',

    endpointNotifications: '/api/notification',
    notificationsRole: "PARENT",

    init: function () {

        // evaluate url parameters and set/delete a cookie if neccessary
        if (getUrlParameter('preapprovalSuccessful') == 'true') {
            // sets a 'preapproved cookie, if the url parameter is set
            document.cookie = 'preapproved=true;';
        }
        if (getUrlParameter('preapprovalSuccessful') == 'false') {
            // sets a 'preapproved cookie, if the url parameter is set
            document.cookie = 'preapproved=;';
        }
        // get the preapproved cookie
        var preapprovedCookie = document.cookie.replace(
            /(?:(?:^|.*;\s*)preapproved\s*\=\s*([^;]*).*$)|^.*$/, "$1");
        // and hide the paypal button if the cookie is set.
        if (preapprovedCookie == 'true') {
            $('#preapprovalListItem').css("visibility", "hidden");
        }

        // set event listeners
        $(document).on('click', '.updateState', function (e) {
            e.preventDefault();

            var data = {
                taskId: $(this).attr('data-id'),
                status: $(this).attr('data-state')
            };
            Parent.updateTask(data);
        });

        $(document).on('click', '.delete', function (e) {
            e.preventDefault();
            var taskId = $(this).attr('data-id');
            Parent.deleteTask(taskId);
        });

        $(document).on('click', '#populateTasks', function (e) {
            e.preventDefault();
            Parent.populateTasks();
        });

        $('#taskFormTg').click(function (e) {
            $('#newTaskFormPanel').toggle();
        });

        $('#newTaskForm').submit(function (e) {
            e.preventDefault();
            Parent.createTask($(this).serialize());
            $('#newTaskFormPanel').hide();
        });

        setTimeout(Parent.checkNotifications, 3000);
    },

    checkNotifications: function () {
        $.getJSON(Parent.endpointNotifications, {'role': Parent.notificationsRole},
            function (data) {
                if (data == true) {
                    Parent.loadTasks();
                }
                setTimeout(Parent.checkNotifications, 3000);
            });
    },

    loadTasks: function loadTasks() {
        $.getJSON(Parent.endpoint, function (tasks) {
            Parent.clearTaskContainer();

            for (var i in tasks) {
                var task = tasks[i];
                var taskDom = Parent.taskTemplate(task);
                $("#tasksCollection").append(taskDom);
            }
        });
    },

    clearTaskContainer: function () {
        $('#tasksCollection').html("");
    },

    deleteTask: function (id) {
        $.ajax({
            url: Parent.endpoint + "?taskId=" + id,
            type: 'DELETE',
            success: Parent.loadTasks,
            error: Parent.reqError
        });
    },

    updateTask: function (data) {
        $.ajax({
            url: Parent.endpoint,
            data: data,
            type: 'POST',
            success: Parent.loadTasks,
            error: Parent.reqError
        });
    },

    createTask: function (data) {
        $.ajax({
            url: Parent.endpoint,
            data: data,
            type: 'PUT',
            success: Parent.loadTasks,
            error: Parent.reqError
        });
    },

    populateTasks: function () {
        $.get('/api/populate', function () {
            Parent.loadTasks();
        });
    },

    reqError: function (xhr, status, error) {
        Parent.errorTemplate.children('span').html(xhr.responseText);
        Parent.errorTemplate.show();
    }

};

function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) {
            return sParameterName[1];
        }
    }
};