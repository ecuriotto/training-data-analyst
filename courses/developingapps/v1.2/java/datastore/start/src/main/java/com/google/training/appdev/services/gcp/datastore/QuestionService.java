/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.training.appdev.services.gcp.datastore;

import com.google.cloud.datastore.*;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.datastore.v1.Filter;
import com.google.training.appdev.services.gcp.domain.Question;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {

// 1: Create a Datastore client object, datastore
// The DatastoreOptions class has a getDefaultInstance()
// static method.
// Use the getService() method of the DatastoreOptions
// object to get the Datastore client


private Datastore datastore = DatastoreOptions.getDefaultInstance().getService();


// 2: Declare a static final String named kind
//The Datastore key is the equivalent of a primary key in a // relational database.
// There are two main ways of writing a key:
// 1. Specify the kind, and let Datastore generate a unique //    numeric id
// 2. Specify the kind and a unique string id
private static final String ENTITY_KIND = "Question";




// 3: Create a KeyFactory for Question entities
private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(ENTITY_KIND);

// The createQuestion(Question question) method
// is passed a Question object using data from the form
// Extract the form data and add it to Datastore

// 4: Modify return type to Key

    public Key createQuestion(Question question) {
// 5: Declare the entity key,
// with a Datastore allocated id
         Key key =datastore.allocateId(keyFactory.newKey());
        Entity questionEntity = Entity.newBuilder(key)
                .set(Question.QUIZ,question.getQuiz())
                .set(Question.AUTHOR, question.getAuthor())
                .set(Question.ANSWER_ONE, question.getAnswerOne())
                .set(Question.ANSWER_TWO, question.getAnswerTwo())
                .set(Question.ANSWER_THREE, question.getAnswerThree())
                .set(Question.ANSWER_FOUR, question.getAnswerFour())
                .build();
        // 6: Save the entity
        datastore.put(questionEntity);

        // 7: Return the key
        return key;
    }

    public List<Question> getAllQuestions(String quiz){

// 8: Remove this code
/*
        List<Question> questions = new ArrayList<>();
        Question dummy = new Question.Builder()
                .withQuiz("gcp")
                .withAuthor("Dummy Author")
                .withTitle("Dummy Title")
                .withAnswerOne("Dummy Answer One")
                .withAnswerTwo("Dummy Answer Two")
                .withAnswerThree("Dummy Answer Three")
                .withAnswerFour("Dummy Answer Four")
                .withCorrectAnswer(1)
                .withId(-1)
                .build();
        questions.add(dummy);

        return questions;
*/



 // 9: Create the query
 // The Query class has a static newEntityQueryBuilder() 
 // method that allows you to specify the kind(s) of 
 // entities to be retrieved.
 // The query can be customized to filter the Question 
 // entities for one quiz.

       Query questionQuery = Query.newEntityQueryBuilder().setKind(ENTITY_KIND).build();


 // 10: Execute the query
        List<Question> questions = new ArrayList<>();
        QueryResults<Entity> queryResults = datastore.run(questionQuery);
        queryResults.forEachRemaining(s ->{

        });

        questions = buildQuestions(queryResults);
        return questions;
    }


// 11: Uncomment this block

    private List<Question> buildQuestions(Iterator<Entity> entities){
        List<Question> questions = new ArrayList<>();
        entities.forEachRemaining(entity-> questions.add(entityToQuestion(entity)));
        return questions;
    }

    private Question entityToQuestion(Entity entity){
        return new Question.Builder()
                .withQuiz(entity.getString(Question.QUIZ))
                .withAuthor(entity.getString(Question.AUTHOR))
                .withTitle(entity.getString(Question.TITLE))
                .withAnswerOne(entity.getString(Question.ANSWER_ONE))
                .withAnswerTwo(entity.getString(Question.ANSWER_TWO))
                .withAnswerThree(entity.getString(Question.ANSWER_THREE))
                .withAnswerFour(entity.getString(Question.ANSWER_FOUR))
                .withCorrectAnswer(entity.getLong(Question.CORRECT_ANSWER))
                .withId(entity.getKey().getId())
                .build();
    }


}
