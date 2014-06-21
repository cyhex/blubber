/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    console.log("here child.js");

    var callbackUrl = "http://" + window.location.host +
            "/html/child/approve.html?code={CODE}";

    var buyUrl = "http://zxing.appspot.com/scan?ret=" + encodeURIComponent(callbackUrl);

    $("#buy").attr('href', buyUrl);
});


var Child = {
    // cache and compile template
    taskTemplate: _.template($("#taskItem").html()),
    indicatorTemplate: _.template($("#indicatorItem").html()),
    // REST end point
    endpoint: '/api/child/task',
    endpointNotifications: '/api/notification',
    notificationsRole: "CHILD",
    init: function() {

        // set event listeners
        $(document).on('click', '.updateState', function(e) {
            e.preventDefault();

            var data = {
                taskId: $(this).attr('data-id'),
                status: $(this).attr('data-state')
            };
            Child.updateTask(data);
        });

        setTimeout(Child.checkNotifications, 3000);
    },
    checkNotifications: function() {
        $.getJSON(Child.endpointNotifications, {'role': Child.notificationsRole},
        function(data) {
            if (data == "true") {
                Child.loadTasks();
            }
            setTimeout(Child.checkNotifications, 3000);
        });
    },
    loadTasks: function loadTasks() {
        /*$.getJSON(Child.endpoint, function (tasks) {
         Child.clearTaskContainer();
         
         for (var i in tasks) {
         var task = tasks[i];
         var taskDom = Child.taskTemplate(task);
         $("#tasksCollection").append(taskDom);
         }
         });
         */
        var json = '[{"key":{"kind":"Task","id":4613069753810944},"propertyMap":{"title":"my Task title 5","description":"Compiles JavaScript templates"}}, {"key":{"kind":"Task","id":4713069753810944},"propertyMap":{"title":"my Task title 4","description":"Compiles JavaScript templates"}}]' ;
        
        var tasks = JSON.parse(json);
        var id = tasks[0].key.id;
        
        console.log(id);
        console.log(tasks);
        for (var i in tasks) {
           
            var task = tasks[i];
             if(i == 0){
                task.active=true;
            }
            else {
                task.active=false;
            }
            var taskDom = Child.taskTemplate({task:task});
            var indicator = Child.indicatorTemplate({data:i});
            $("#tasksCollection").append(taskDom);
            $("#indicators").append(indicator);
        }
    },
    clearTaskContainer: function() {
        $('#tasksCollection').html("");
    },
    updateTask: function(data) {
        $.ajax({
            url: Child.endpoint,
            data: data,
            type: 'POST',
            success: Child.loadTasks,
            error: Child.reqError
        });
    },
    reqError: function(xhr, status, error) {
        Child.errorTemplate.children('span').html(xhr.responseText);
        Child.errorTemplate.show();
    }

};




