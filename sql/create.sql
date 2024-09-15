create table person(
    id serial primary key,
    full_name text unique not null,
    birth_year int not null
)

insert into person(full_name, birth_year)
values
('Иванов Петр Дмитриевич', 1990),
('Петров Александр Сергеевич', 1985),
('Сидоров Захар Дмитриевич', 1993),
('Степанов Иван Олегович', 1991),
('Григорьев Максим Александрович', 1986)

create table book(
    id serial primary key,
    person_id int references person(id) on delete set null,
    title text not null,
    author text not null,
    year int not null,
    taken_at timestamp
)

insert into book(title, author, year)
values
('Мастер и Маргарита','Михаил Булгаков',1940),
('Мёртвые души','Николай Гоголь',1842),
('Граф Монте-Кристо','Александр Дюма',1845),
('Три товарища','Эрих Мария Ремарк',1936),
('Отверженные','Виктор Гюго',1862),
('Евгений Онегин','Александр Пушкин',1837),
('Преступление и наказание','Федор Достоевский',1866)

update book set person_id=1 where id=1
update book set person_id=1 where id=2
update book set person_id=2 where id=3
update book set person_id=2 where id=4
update book set taken_at=now()-interval '11 day' where id=1
update book set taken_at=now()-interval '5 day' where id=2
update book set taken_at=now()-interval '50 day' where id=3
update book set taken_at=now()-interval '7 day' where id=4
