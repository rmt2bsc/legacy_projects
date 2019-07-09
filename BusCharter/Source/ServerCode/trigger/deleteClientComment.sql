create trigger deleteClientComment 
        before delete on client
        referencing old as tbl
        for each row

  begin
       declare l_comment_id int;

          /*  Ensure that the comment actually exist in the comment table  */  
       select comment_id
            into l_comment_id
           from comments
           where comments.comment_id = tbl.comment_id;

       if sqlcode = 0 then
           delete from comments where comment_id = l_comment_id;
           if sqlcode <> 0 then
                raiserror 30000 'Update error occured regarding the Client comments for client, ' ||tbl.id|| '.   Delete operation aborted!';
           end if;
      end if;
end;
