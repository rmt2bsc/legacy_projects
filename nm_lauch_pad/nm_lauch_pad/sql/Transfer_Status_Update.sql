--select * from transfer where tranfr_no = 860216698001010

--select tranfr_sku_overflw, tranfr_sku, tranfr_qty_snt, tranfr_qty_rec, tranfr_qty_req from tranfr_item where tranfr_no = 1202270

update transfer set tranfr_status = 'R' where tranfr_no = 860216698001010