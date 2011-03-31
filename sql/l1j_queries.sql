
-- find mops that drop item
select n.name, n.npcid, d.chance from npc n, droplist d where n.npcid = d.mobid and d.itemid = 188 ;

-- find armor drops of mob
select 	n.name, a.name, d.chance from npc n, droplist d, armor a where n.npcid = d.mobid and a.item_id = d.itemid and n.npcid = 45553 ;

-- find etcitem drops of mob
select 	n.name, i.name, i.item_id, d.chance from npc n, droplist d, etcitem i where n.npcid = d.mobid and i.item_id = d.itemid and n.npcid = 45553 ;

-- find weapon drops of mob
select 	n.name, w.name, d.chance from npc n, droplist d, weapon w where n.npcid = d.mobid and w.item_id = d.itemid and n.npcid = 45553 ;

-- get boss list
select sb.mapid, n.npcid, n.name from spawnlist_boss sb, npc n where n.npcid = sb.npc_id order by n.lvl asc ;

-- full carried items audit
select c.char_name, ci.item_name, ci.item_id, ci.enchantlvl from characters c, character_items ci where c.objid = ci.char_id and c.isgm = 0 order by c.char_name;



-- check heavy throwing knife name
