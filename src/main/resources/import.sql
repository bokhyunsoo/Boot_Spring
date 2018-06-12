insert into user (id, user_id, password, name, email, create_date) values (1,'bhs','test','복현수','bhs@gmail.com',current_timestamp());
insert into user (id, user_id, password, name, email, create_date) values (2,'aaa','test','aaa','aaa@gmail.com',current_timestamp());

insert into question (id, writer_id, title, contents, create_date, count_of_answer) values (1,1,'test1','test1',current_timestamp(), 0);
insert into question (id, writer_id, title, contents, create_date, count_of_answer) values (2,2,'test2','test2',current_timestamp(), 0);