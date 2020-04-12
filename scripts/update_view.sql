CREATE OR REPLACE FUNCTION public.update_view(
	)
    RETURNS integer
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$

    DECLARE
	viewMonth TEXT;
	viewYear TEXT;
	query TEXT;

    BEGIN
	select month, year
	from view_question
	into  viewMonth,viewYear
	where year = (select max(year) from view_question)
	and month = (select max(month)
	      		 from view_question
				 where year = (select max(year) from view_question));

	if viewMonth is null then
		viewMonth := '03';
		viewYear := '2012';
	end if;

	query := 'delete from view_question where month=' || viewMonth || ' and year=' || viewYear;

	execute query;

	query := 'insert into view_question (month,year,number) ';
	query := query || 'select extract(month from creationdate) as MM,';
	query := query || 'extract(year from creationdate) as yyyy,';
	query := query || 'count(id) as number ';
	query := query || 'from question where creationdate >=''' || viewYear || '-' || viewMonth || '-01'' ';
	query := query || 'group by 1,2 ';
	query := query || 'order by 2';

	execute query;

	return 1;

    END;
    $BODY$;

