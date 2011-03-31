select 
    distinct s1.item_id 
from 
    shop s1 
where 
    not (s1.npc_id < 81030 and s1.npc_id > 81001) 
and s1.selling_price  <> -1 
and exists 
      ( select 'x' 
        from shop s2 
        where 
            s1.item_id = s2.item_id 
        and s2.purchasing_price > s1.selling_price 
        and s2.purchasing_price <> -1 
        and not (s1.npc_id < 81030 and s1.npc_id > 81001)
      );
