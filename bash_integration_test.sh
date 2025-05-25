#!/bin/bash
IP_HOST='http://localhost:8080'
API_AUDITORIES='/api/auditories'
API_AUDITORY_TYPE='/api/auditory-types'
API_DEPARTMENTS='/api/departments'
API_FACULTIES='/api/faculties'
API_GROUPS='/api/groups'
API_LECTURIES='/api/lecturies'
API_STUDENTS='/api/students'
API_SUBJECTS='/api/subjects'
API_TEACHERS='/api/teachers'
API_TIMETABLES='/api/timetable-items'

randomNumerious(){
    local length
    local RANDOM_UUID
    if [[ $# -eq 0 ]] ; then
    length=18
    else length=$1
    fi

    < /dev/urandom tr -dc 0-9 | head -c "$length"
}

randomUUID(){
  local length
  local RANDOM_UUID
  if [[ $# -eq 0 ]] ; then
  length=18
  else length=$1
  fi

  RANDOM_UUID=$(uuidgen -r)
  echo "${RANDOM_UUID:0:$length}"
}

sendPostRequest (){
     local URL=$1
     local JSON_DATA=$2
       responsePost=$(curl -X POST -H "Content-Type: application/json" -d "$JSON_DATA" --url "$IP_HOST$URL" )
       echo "Response POST $responsePost"
}

sendPostAuditoryRequest(){
    local RANDOM_UUID=$(randomUUID)
    local JSON_DATA='{
                   "id": 0,
                   "auditoryNumber": "'$RANDOM_UUID'",
                   "auditoryTypeTitle": "title '$RANDOM_UUID'",
                   "auditoryTypeId": 2,
                   "maxCapacity": 40,
                   "description": "description string"
                 }'
      sendPostRequest $API_AUDITORIES "$JSON_DATA";
}

sendPostAuditoryTypeRequest(){
    local RANDOM_UUID=$(randomUUID)
    local JSON_DATA='{
                       "id": 0,
                       "type": "type '$RANDOM_UUID'"
                     }'
      sendPostRequest $API_AUDITORY_TYPE "$JSON_DATA";
}

sendPostDepartmentsRequest(){
    local RANDOM_UUID=$(randomUUID)
    local JSON_DATA='{
                       "id": 0,
                       "title": "'$RANDOM_UUID'",
                       "description": "description",
                       "facultyTitle": "facultyTitle",
                       "facultyId": 3
                     }'
      sendPostRequest $API_DEPARTMENTS "$JSON_DATA";
}

sendPostFacultiesRequest(){
    local RANDOM_UUID=$(randomUUID)
    local JSON_DATA='{
                       "id": 0,
                       "title": "'$RANDOM_UUID'"
                     }'
      sendPostRequest $API_FACULTIES "$JSON_DATA";
}

sendPostGroupsRequest(){
    local RANDOM_UUID=$(randomUUID)
    local JSON_DATA='{
                       "id": 0,
                       "title": "'$RANDOM_UUID'",
                       "yearEntry": 2100
                     }'
      sendPostRequest $API_GROUPS "$JSON_DATA";
}

sendPostLecturiesRequest(){
     local RANDOM_UUID=$(randomUUID 5)
     local JSON_DATA='{
                        "id": 6,
                        "number": "'$RANDOM_UUID'",
                        "startTime": "16:40:00",
                        "endTime": "18:15:00"
                      }'
       sendPostRequest $API_LECTURIES "$JSON_DATA";
 }

sendPostStudentsRequest(){
      local RANDOM_UUID=$(randomUUID)
      local JSON_DATA='{
                         "id": 0,
                         "firstName": "'$RANDOM_UUID'",
                         "middleName": "string",
                         "lastName": "string",
                         "birthday": "2005-05-25",
                         "idFees": '$(randomNumerious 9)',
                         "groupTitle": "string",
                         "groupId": 2
                       }'
        sendPostRequest $API_STUDENTS "$JSON_DATA";
  }

sendPostSubjectsRequest(){
       local RANDOM_UUID=$(randomUUID)
       local JSON_DATA='{
                          "id": 0,
                          "title": "'$RANDOM_UUID'"
                        }'
         sendPostRequest $API_SUBJECTS "$JSON_DATA";
   }

sendPostTeachersRequest(){
        local RANDOM_UUID=$(randomUUID)
        local JSON_DATA='{
                           "id": 0,
                           "firstName": "'$RANDOM_UUID'",
                           "middleName": "string",
                           "lastName": "string",
                           "birthday": "2005-05-25",
                           "idFees": '$(randomNumerious 9)',
                           "departmentTitle": "string",
                           "departmentId": 2,
                           "subjects": [
                             {
                               "id": 4,
                               "title": "string"
                             }
                           ]
                         }'
          sendPostRequest $API_TEACHERS "$JSON_DATA";
    }

sendPostTimetablesRequest(){
         local RANDOM_UUID=$(randomUUID)
         local JSON_DATA='{
                              "id": 0,
                              "subjectTitle": "Phisics",
                              "subjectId": 2,
                              "auditoryTitle": "102a",
                              "auditoryId": 2,
                              "groups": [
                                {
                                  "id": 3,
                                  "title": "G153",
                                  "yearEntry": 2015
                                },
                                {
                                  "id": 4,
                                  "title": "G154",
                                  "yearEntry": 2015
                                }
                              ],
                              "lectureTitle": "1",
                              "lectureId": 1,
                              "date": "2'$(randomNumerious 3)'-0'$(randomNumerious 1)'-2'$(randomNumerious 1)'",
                              "teacherTitle": "firstNameTe2",
                              "teacherId": 12
                            }'
           sendPostRequest $API_TIMETABLES "$JSON_DATA";
     }

sendGetRequest(){
  local url=$1
  curl --request GET --url "$IP_HOST$url"
}

 for i in {1..220} ; do
   sendPostAuditoryRequest &
   sendPostAuditoryTypeRequest &
   sendPostDepartmentsRequest &
   sendPostFacultiesRequest &
   sendPostGroupsRequest &
   sendPostLecturiesRequest &
   sendPostStudentsRequest &
   sendPostSubjectsRequest &
   sendPostTeachersRequest &
   sendPostTimetablesRequest &
   sendGetRequest $API_AUDITORIES &
   sendGetRequest $API_AUDITORY_TYPE &
   sendGetRequest $API_DEPARTMENTS &
   sendGetRequest $API_FACULTIES &
   sendGetRequest $API_GROUPS &
   sendGetRequest $API_LECTURIES &
   sendGetRequest $API_STUDENTS &
   sendGetRequest $API_SUBJECTS &
   sendGetRequest $API_TEACHERS &
   sendGetRequest $API_TIMETABLES &
 done
echo "End of requests"