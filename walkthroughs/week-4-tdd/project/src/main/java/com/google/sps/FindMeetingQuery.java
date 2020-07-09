// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java. util. Iterator;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    //throw new UnsupportedOperationException("TODO: Implement this method.");
    Collection<TimeRange> timeAvailable = Collections.emptySet();

//have to check if all attendies can return

         //fucntion //need to return a collection of time ranges that will work

         //in this if statement, if events is empty, return all possible event times given duration of meeting on that day
 if(request.getAttendees().isEmpty() &&  request.getDuration() > 0){
timeAvailable = Arrays.asList(TimeRange.WHOLE_DAY);
           return timeAvailable;
 }

//
 if( request.getAttendees() != null && request.getDuration() > TimeRange.WHOLE_DAY.duration()){
     timeAvailable = Arrays.asList();
      return timeAvailable; 
  }
  
  if(request.getAttendees() != null &&  events.size() == 1){
      
      //basically the starting time of the event that your requesting
      Event first = events.iterator().next();
    int startingTime = first.getWhen().start();
    int dayStarTime = TimeRange.START_OF_DAY;
    int dayEndTime = TimeRange.END_OF_DAY;
    
    if(startingTime > dayStarTime){
        //now want to see the difference between them

     int timetoStartAfterMeeting = startingTime + first.getWhen().duration();
        timeAvailable = Arrays.asList(TimeRange.fromStartEnd(dayStarTime, startingTime, false),TimeRange.fromStartEnd(timetoStartAfterMeeting, dayEndTime, true));
      return timeAvailable;
    }
    
      
  }

  if(request.getAttendees() != null && events.size() == 0){
      timeAvailable = Arrays.asList(TimeRange.WHOLE_DAY);
      return timeAvailable;
  }

 return timeAvailable;
  }

  


}
