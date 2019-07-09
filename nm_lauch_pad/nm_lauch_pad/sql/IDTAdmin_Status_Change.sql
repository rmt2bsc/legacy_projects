--select count(*) from idt_retick where status = 'E' and status_date > '2012-05-26'

update idt_retick set status = 'A' where status = 'E' and status_date > '2012-05-26'