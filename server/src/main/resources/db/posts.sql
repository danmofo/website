use `dmoffat.com`;

select *
  from post;

select *
  from post_content_revision;
  
select distinct p.id
  from post p
  left join post_tag pt
    on p.id = pt.post_id
  join tag t
    on t.id = pt.tag_id;
    