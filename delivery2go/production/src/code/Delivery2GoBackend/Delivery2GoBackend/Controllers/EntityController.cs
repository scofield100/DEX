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
	public class EntityController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Entity> queryBuider;

		public EntityController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<Entity>()
				.MapMember("ImageLocation", x => x.Image.Location)
				.MapMember("CityName", x => x.City.Name)
				.MapMember("StateName", x => x.State.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/entity/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Entities.AsNoTracking(), filter, orderby, skip, top, x => new
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

		// GET api/entity/get/5
		public object Get(int id)
		{
			return context.Entities.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
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
				})
			.FirstOrDefault();
		}

		// POST api/entity/
		public HttpResponseMessage Post([FromBody]Entity value)
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
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/entity/
		public HttpResponseMessage Put([FromBody]Entity value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (filter == null)
			{
				context.Entry(value).State = System.Data.EntityState.Modified;
			}
			else
			{
				var old = context.Entities.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.Ranking = value.Ranking;
				old.ReviewCount = value.ReviewCount;
				old.ImageId = value.ImageId;
				old.Adress = value.Adress;
				old.CityId = value.CityId;
				old.StateId = value.StateId;
				old.Lat = value.Lat;
				old.Lng = value.Lng;
				old.Phone = value.Phone;
				old.Email = value.Email;
				old.Tags = value.Tags;
				old.OpeningTime = value.OpeningTime;
				old.CloseTime = value.CloseTime;
				old.Description = value.Description;
				old.IsAvailable = value.IsAvailable;
				old.DeliveryPrice = value.DeliveryPrice;
				old.DeliveryTimeMin = value.DeliveryTimeMin;
				old.DeliveryTimeMax = value.DeliveryTimeMax;
				old.MinPrice = value.MinPrice;
				old.CreateDate = value.CreateDate;
				old.HasDelivery = value.HasDelivery;
				old.HasPickup = value.HasPickup;
				old.Account = value.Account;
				old.UpdateUserId = value.UpdateUserId;
				old.UpdateDate = value.UpdateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/entity/
		public bool Delete(string filter = null)
		{
			IQueryable<Entity> query = context.Entities;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}

			foreach (var item in query.ToList())
			{
                foreach (var image in context.EntityImageses
                   .Where(x => x.EntityId == item.Id)
                   .Select(x => x.Image))
                {
                    context.Images.Remove(image);
                }

				context.Entities.Remove(item);
			}

			return context.SaveChanges() > 0;
		}

		// GET api/entity/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Entity> query = context.Entities.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/entity/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Entities.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
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
