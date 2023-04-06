create table Member
(
    id int auto_increment,
    email varchar(20) not null,
    nickname varchar(20) not null,
    birthday date not null,
    createdAt datetime not null,
    constraint member_id_uindex
        primary key (id)
);

create table MemberNicknameHistory
(
    id int auto_increment,
    memberId int not null,
    nickname varchar(20) not null,
    createdAt datetime not null,
    constraint memberNicknameHistory_id_uindex
        primary key (id)
);

create table Follow
(
    id int auto_increment,
    fromMemberId int not null,
    toMemberId int not null,
    createdAt datetime not null,
    constraint Follow_id_uindex
        primary key (id)
);

create unique index Follow_fromMemberId_toMemberId_uindex
    on Follow (fromMemberId, toMemberId);


create table POST
(
    id int auto_increment,
    memberId int not null,
    contents varchar(100) not null,
    createdDate date not null,
    createdAt datetime not null,
    constraint POST_id_uindex
        primary key (id)
);

create index POST__index_member_id
    on POST (memberId);

create index POST__index_created_date
    on POST (createdDate);

create index POST__index_member_id_created_date
    on POST (memberid, createdDate);



select count(id) from POST;

drop table post;

explain SELECT memberId, createdDate, count(id) as cnt
FROM POST use index (POST__index_member_id_created_date)
WHERE memberId = 2 and createdDate between '1900-01-01' and '2023-01-01'
GROUP BY memberId, createdDate;

select memberId, count(id)
from POST
group by memberId;

select createdDate, count(id)
from POST
group by createdDate
order by 2 desc ;

explain select count(distinct createdDate)
from POST;

select *
from POST
where memberId = 4 and id > 1000;

select *
from POST
where memberId = 4;