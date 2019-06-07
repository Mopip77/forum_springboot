create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int not null,
	commentator int not null,
	gmt_create bigint not null,
	gmt_modified bigint not null,
	like_count int default 0,
	constraint comment_pk
		primary key (id)
);

comment on column comment.parent_id is '父问题id';

comment on column comment.type is '父类类型(区分是第几级评论)';

comment on column comment.commentator is '评论人id';

comment on column comment.like_count is '点赞数';