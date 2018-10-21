1. 

   Data

   1. Moving data from Sqlite3 to MySQL
   2. Fixing table ra_question
   3. I realized that I should install a driver and stuff. I should maybe just generate the tables with the data that I want to visualize and that's all.

   

   ------

   # Describing data

   ### **Basic statistic description**: questions, users and tags

   ### **Questions**

   Building table: `id, title_length , author, added_at, body_length, view_count, votes, nb_of_tags, nb_of_answers, nb_of_comments`

   ```sql lite
   -- Number of answers per question: id_question, nb_of_answers
   select ros_question_answer.ros_question_id as id_question,
          Count(*)                            as nb_of_answers
   from   (select id
           from   ros_answer
           where  type = 'answer') as ra
          join ros_question_answer
            on ros_question_answer.ros_answer_id = ra.id
   group  by ros_question_id  
   
   ```

   ```sql lite
   -- Number of comments per question: id_question, nb_of_comments
   select ros_question_answer.ros_question_id as id_question,
          Count(*)                            as nb_of_answers
   from   (select id
           from   ros_answer
           where  type = 'comment') as ra
          join ros_question_answer
            on ros_question_answer.ros_answer_id = ra.id
   group  by ros_question_id  
   
   ```

   ```sql lite
   -- Number of tags per question: id_question, nb_of_tags
    select ros_question_id as id_question,
          Count(*)        as nb_of_tags
   from   ros_question_tag
   group  by ros_question_id  
   ```

   All together:

   ```sql lite
   select id, title , author, added_at, body, view_count, votes,  QTA.nb_of_tags, QTA.nb_of_answers, C.nb_of_comments
   from 
   (
       select *
       from
       (
           select id, title , author, added_at, body, view_count, votes, T.nb_of_tags as nb_of_tags
           from ros_question
           left join (
               select ros_question_id as id_question, Count(*) as nb_of_tags
               from ros_question_tag
               group by ros_question_id
           ) as T on ros_question.id = T.id_question
       ) as QT
       left join (
       -- Number of answers per question: id_question, nb_of_answers
       select ros_question_answer.ros_question_id as id_question,
              Count(*)                            as nb_of_answers
       from   (select id
               from   ros_answer
               where  type = 'answer') as ra
              join ros_question_answer
                on ros_question_answer.ros_answer_id = ra.id
       group  by ros_question_id  
       ) as A on QT.id = A.id_question
   ) as QTA 
   left join (
       -- Number of comments per question: id_question, nb_of_comments
       select ros_question_answer.ros_question_id as id_question,
              Count(*)                            as nb_of_comments
       from   (select id
               from   ros_answer
               where  type = 'comment') as ra
              join ros_question_answer
                on ros_question_answer.ros_answer_id = ra.id
       group  by ros_question_id  
       ) as C on QTA.id = C.id_question
   
   ```

   Distribution of tags per answered questions/unanswered

   

   Number of tags for questions WITH/WITHOUT validated answer

   

   

   # Changes in db

   1. Table ra_answers --> Table ra_answers / ra_comments
   2. Table gh_commits --> más info 