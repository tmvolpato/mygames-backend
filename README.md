# mygames-backend
API My Games


# Create Docker container to BD

Create volume 
> $ docker volume create --name dbdata

Create network
> $ docker network create --driver bridge my-rede

Create container postgres
> sudo docker run --name postgres9 --network=my-rede -e "POSTGRES_PASSWORD=your-password" -p 5432:5432 -d -v dbdata:/var/lib/postgresql/data postgres

Create container pgAdmin
> sudo docker run --name postgres-pgadmin --network=my-rede -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=your-email" -e "PGADMIN_DEFAULT_PASSWORD=your-password" -d dpage/pgadmin4
