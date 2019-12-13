"use strict"

function loadSut(target, profile_uri){
    $.post("/profiles/" + profile_uri + "/suts/new", function(result){
        $(target).text(result)
    })
}
