CREATE TABLE IF NOT EXISTS task
(
    id          varchar(100) primary key,
    title       varchar(20),
    description varchar(100),
    status      varchar(10)
);

DELETE
FROM task;

INSERT INTO task (id, title, description, status)
VALUES ('1', 'Title 1', 'Description 1', 'NEW'),
       ('2', 'Title 2', 'Description 2', 'BLOCKED'),
       ('3', 'Title 3', 'Description 3', 'COMPLETED'),
       ('4', 'Title 4', 'Description 4', 'NEW'),
       ('5', 'Title 5', 'Description 5', 'BLOCKED'),
       ('6', 'Title 6', 'Description 6', 'COMPLETED'),
       ('7', 'Title 7', 'Description 7', 'NEW'),
       ('8', 'Title 8', 'Description 8', 'BLOCKED'),
       ('9', 'Title 9', 'Description 9', 'COMPLETED'),
       ('10', 'Title 10', 'Description 10', 'NEW'),
       ('11', 'Title 11', 'Description 11', 'BLOCKED'),
       ('12', 'Title 12', 'Description 12', 'COMPLETED'),
       ('13', 'Title 13', 'Description 13', 'NEW'),
       ('14', 'Title 14', 'Description 14', 'BLOCKED'),
       ('15', 'Title 15', 'Description 15', 'COMPLETED'),
       ('16', 'Title 16', 'Description 16', 'NEW'),
       ('17', 'Title 17', 'Description 17', 'BLOCKED'),
       ('18', 'Title 18', 'Description 18', 'COMPLETED'),
       ('19', 'Title 19', 'Description 19', 'NEW'),
       ('20', 'Title 20', 'Description 20', 'BLOCKED'),
       ('21', 'Title 21', 'Description 21', 'COMPLETED'),
       ('22', 'Title 22', 'Description 22', 'NEW'),
       ('23', 'Title 23', 'Description 23', 'BLOCKED'),
       ('24', 'Title 24', 'Description 24', 'COMPLETED'),
       ('25', 'Title 25', 'Description 25', 'NEW'),
       ('26', 'Title 26', 'Description 26', 'BLOCKED'),
       ('27', 'Title 27', 'Description 27', 'COMPLETED'),
       ('28', 'Title 28', 'Description 28', 'NEW'),
       ('29', 'Title 29', 'Description 29', 'BLOCKED'),
       ('30', 'Title 30', 'Description 30', 'COMPLETED');

