create or replace view public.smestuvanje_stats_by_user(korisnik, count) as
SELECT smestuvanje.korisnik AS korisnik,
       count(*) AS count
FROM smestuvanje
GROUP BY smestuvanje.korisnik;

alter table public.smestuvanje_stats_by_user
    owner to emt;


create materialized view if not exists public.smestuvanje_stats_by_host as
SELECT host_id,count(*) AS number_of_smestuvanje
FROM smestuvanje
GROUP BY host_id;

create materialized view if not exists public.host_by_country as
select country_id,count(*) as number_of_hosts
from host
group by country_id


