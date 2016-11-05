using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Enterlib;
using Enterlib.Parsing;
using Enterlib.Validation;
using Delivery2GoBackend.Models;

namespace Delivery2GoBackend.Controllers
{
	public class ClientEntityFavoritesEntityController : ApiController
	{

		deliveryContext context;
		QueryBuilder<ClientEntityFavorites> queryBuider;
		QueryBuilder<Entity> distintQueryBuider;

		public ClientEntityFavoritesEntityController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<ClientEntityFavorites>()
				.MapMember("Id", x => x.Entity.Id)
				.MapMember("Name", x => x.Entity.Name)
				.MapMember("Ranking", x => x.Entity.Ranking)
				.MapMember("ReviewCount", x => x.Entity.ReviewCount)
				.MapMember("ImageId", x => x.Entity.ImageId)
				.MapMember("Adress", x => x.Entity.Adress)
				.MapMember("CityId", x => x.Entity.CityId)
				.MapMember("StateId", x => x.Entity.StateId)
				.MapMember("Lat", x => x.Entity.Lat)
				.MapMember("Lng", x => x.Entity.Lng)
				.MapMember("Phone", x => x.Entity.Phone)
				.MapMember("Email", x => x.Entity.Email)
				.MapMember("Tags", x => x.Entity.Tags)
				.MapMember("OpeningTime", x => x.Entity.OpeningTime)
				.MapMember("CloseTime", x => x.Entity.CloseTime)
				.MapMember("Description", x => x.Entity.Description)
				.MapMember("IsAvailable", x => x.Entity.IsAvailable)
				.MapMember("DeliveryPrice", x => x.Entity.DeliveryPrice)
				.MapMember("DeliveryTimeMin", x => x.Entity.DeliveryTimeMin)
				.MapMember("DeliveryTimeMax", x => x.Entity.DeliveryTimeMax)
				.MapMember("MinPrice", x => x.Entity.MinPrice)
				.MapMember("CreateDate", x => x.Entity.CreateDate)
				.MapMember("HasDelivery", x => x.Entity.HasDelivery)
				.MapMember("HasPickup", x => x.Entity.HasPickup)
				.MapMember("Account", x => x.Entity.Account)
				.MapMember("UpdateUserId", x => x.Entity.UpdateUserId)
				.MapMember("UpdateDate", x => x.Entity.UpdateDate)
				.MapMember("ImageLocation", x => x.Entity.Image.Location)
				.MapMember("CityName", x => x.Entity.City.Name)
				.MapMember("StateName", x => x.Entity.State.Name)
				.MapMember("UpdateUserName", x => x.Entity.UpdateUser.Name);

			distintQueryBuider = new QueryBuilder<Entity>()
				.MapMember("ImageLocation", x => x.Image.Location)
				.MapMember("CityName", x => x.City.Name)
				.MapMember("StateName", x => x.State.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/cliententityfavoritesentity/get/
		public System.Collections.ICollection Get(int ClientId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.ClientEntityFavoriteses.AsNoTracking().Where(x=>x.ClientId == ClientId), filter, orderby, skip, top, x => new
					{
						Id=x.Entity.Id,
						Name=x.Entity.Name,
						Ranking=x.Entity.Ranking,
						ReviewCount=x.Entity.ReviewCount,
						ImageId=x.Entity.ImageId,
						ImageLocation=x.Entity.Image.Location,
						Adress=x.Entity.Adress,
						CityId=x.Entity.CityId,
						CityName=x.Entity.City.Name,
						StateId=x.Entity.StateId,
						StateName=x.Entity.State.Name,
						Lat=x.Entity.Lat,
						Lng=x.Entity.Lng,
						Phone=x.Entity.Phone,
						Email=x.Entity.Email,
						Tags=x.Entity.Tags,
						OpeningTime=x.Entity.OpeningTime,
						CloseTime=x.Entity.CloseTime,
						Description=x.Entity.Description,
						IsAvailable=x.Entity.IsAvailable,
						DeliveryPrice=x.Entity.DeliveryPrice,
						DeliveryTimeMin=x.Entity.DeliveryTimeMin,
						DeliveryTimeMax=x.Entity.DeliveryTimeMax,
						MinPrice=x.Entity.MinPrice,
						CreateDate=x.Entity.CreateDate,
						HasDelivery=x.Entity.HasDelivery,
						HasPickup=x.Entity.HasPickup,
						Account=x.Entity.Account,
						UpdateUserId=x.Entity.UpdateUserId,
						UpdateUserName=x.Entity.UpdateUser.Name,
						UpdateDate=x.Entity.UpdateDate,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.Entities.AsNoTracking().Where(x => !x.ClientEntityFavoriteses.Any(y => y.ClientId == ClientId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Name=x.Name,
						Ranking=x.Ranking,
						ReviewCount=x.ReviewCount,
						ImageId=x.ImageId,
						ImageLocation=x.Image.Location,
						Adress=x.Adress,
						CityId=x.CityId,
						CityName=x.City.Name,
						StateId=x.StateId,
						StateName=x.State.Name,
						Lat=x.Lat,
						Lng=x.Lng,
						Phone=x.Phone,
						Email=x.Email,
						Tags=x.Tags,
						OpeningTime=x.OpeningTime,
						CloseTime=x.CloseTime,
						Description=x.Description,
						IsAvailable=x.IsAvailable,
						DeliveryPrice=x.DeliveryPrice,
						DeliveryTimeMin=x.DeliveryTimeMin,
						DeliveryTimeMax=x.DeliveryTimeMax,
						MinPrice=x.MinPrice,
						CreateDate=x.CreateDate,
						HasDelivery=x.HasDelivery,
						HasPickup=x.HasPickup,
						Account=x.Account,
						UpdateUserId=x.UpdateUserId,
						UpdateUserName=x.UpdateUser.Name,
						UpdateDate=x.UpdateDate,
				}).ToList();
			}
		}

		// GET api/ClientEntityFavoritesEntity/get/5
		public object Get(int id, int ClientId)
		{
			return context.ClientEntityFavoriteses.AsNoTracking().Where(x=>x.ClientId == ClientId && x.Entity.Id == id)
				.Select(x=> new
				{
					Id=x.Entity.Id,
					Name=x.Entity.Name,
					Ranking=x.Entity.Ranking,
					ReviewCount=x.Entity.ReviewCount,
					ImageId=x.Entity.ImageId,
					ImageLocation=x.Entity.Image.Location,
					Adress=x.Entity.Adress,
					CityId=x.Entity.CityId,
					CityName=x.Entity.City.Name,
					StateId=x.Entity.StateId,
					StateName=x.Entity.State.Name,
					Lat=x.Entity.Lat,
					Lng=x.Entity.Lng,
					Phone=x.Entity.Phone,
					Email=x.Entity.Email,
					Tags=x.Entity.Tags,
					OpeningTime=x.Entity.OpeningTime,
					CloseTime=x.Entity.CloseTime,
					Description=x.Entity.Description,
					IsAvailable=x.Entity.IsAvailable,
					DeliveryPrice=x.Entity.DeliveryPrice,
					DeliveryTimeMin=x.Entity.DeliveryTimeMin,
					DeliveryTimeMax=x.Entity.DeliveryTimeMax,
					MinPrice=x.Entity.MinPrice,
					CreateDate=x.Entity.CreateDate,
					HasDelivery=x.Entity.HasDelivery,
					HasPickup=x.Entity.HasPickup,
					Account=x.Entity.Account,
					UpdateUserId=x.Entity.UpdateUserId,
					UpdateUserName=x.Entity.UpdateUser.Name,
					UpdateDate=x.Entity.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/cliententityfavorites/
		public HttpResponseMessage Post(int ClientId, [FromBody]Entity value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new ClientEntityFavorites(){ ClientId=ClientId, Entity=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.ClientEntityFavoriteses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/cliententityfavoritesentity/
		public HttpResponseMessage Put([FromBody]Entity value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			context.Entry(value).State = System.Data.EntityState.Modified;
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/cliententityfavoritesentity/
		public bool Delete(int ClientId, string filter = null)
		{
			IQueryable<ClientEntityFavorites> query = context.ClientEntityFavoriteses.Where(x=>x.ClientId == ClientId);
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.ClientEntityFavoriteses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/cliententityfavoritesentity/count
		[HttpGet]
		public int Count(int ClientId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.ClientEntityFavoriteses.AsNoTracking().Where(x=>x.ClientId == ClientId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.Entities.AsNoTracking().Where(x => !x.ClientEntityFavoriteses.Any(y => y.ClientId == ClientId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/cliententityfavorites/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.ClientEntityFavoriteses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Entity.Id,
					Name=x.Entity.Name,
					Ranking=x.Entity.Ranking,
					ReviewCount=x.Entity.ReviewCount,
					ImageId=x.Entity.ImageId,
					ImageLocation=x.Entity.Image.Location,
					Adress=x.Entity.Adress,
					CityId=x.Entity.CityId,
					CityName=x.Entity.City.Name,
					StateId=x.Entity.StateId,
					StateName=x.Entity.State.Name,
					Lat=x.Entity.Lat,
					Lng=x.Entity.Lng,
					Phone=x.Entity.Phone,
					Email=x.Entity.Email,
					Tags=x.Entity.Tags,
					OpeningTime=x.Entity.OpeningTime,
					CloseTime=x.Entity.CloseTime,
					Description=x.Entity.Description,
					IsAvailable=x.Entity.IsAvailable,
					DeliveryPrice=x.Entity.DeliveryPrice,
					DeliveryTimeMin=x.Entity.DeliveryTimeMin,
					DeliveryTimeMax=x.Entity.DeliveryTimeMax,
					MinPrice=x.Entity.MinPrice,
					CreateDate=x.Entity.CreateDate,
					HasDelivery=x.Entity.HasDelivery,
					HasPickup=x.Entity.HasPickup,
					Account=x.Entity.Account,
					UpdateUserId=x.Entity.UpdateUserId,
					UpdateUserName=x.Entity.UpdateUser.Name,
					UpdateDate=x.Entity.UpdateDate,
				})
			.FirstOrDefault();
		}

		protected override void Dispose(bool disposing)
		{
			if (disposing)
				context.Dispose();
			base.Dispose(disposing);
		}


	}
}
