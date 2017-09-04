use `dmoffat.com`;

select *
  from post;
  
select *
  from post
 where created between '2017-07-17 20:58:36' and '2017-07-17 20:58:36';
 
select *
  from post
  where published <> 1;

select *
  from post_content_revision;
  
select distinct p.id
  from post p
  left join post_tag pt
    on p.id = pt.post_id
  join tag t
    on t.id = pt.tag_id;
    