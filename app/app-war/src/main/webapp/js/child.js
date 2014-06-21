/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
  $(document).ready(function() {

                var callbackUrl = "http://" + window.location.host +
                        "/html/child/approve.html?code={CODE}";

                var buyUrl = "http://zxing.appspot.com/scan?ret=" + encodeURIComponent(callbackUrl);

                $("#buy").attr('href', buyUrl);
            });
 
