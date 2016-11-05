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
	public class DishSizePriceController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishSizePrice> queryBuider;

		public DishSizePriceController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishSizePrice>()
				.MapMember("SizeName", x => x.Size.Name)
				.MapMember("DishName", x => x.Dish.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/dishsizeprice/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.DishSizePrices.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					SizeId=x.SizeId,
					SizeName=x.Size.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					ExtraPrice=x.ExtraPrice,
					AvailableCount=x.AvailableCount,
					UpdateDate=x.UpdateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				}).ToList();
		}

		// GET api/dishsizeprice/get/5
		public object Get(int id)
		{
			return context.DishSizePrices.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					SizeId=x.SizeId,
					SizeName=x.Size.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					ExtraPrice=x.ExtraPrice,
					AvailableCount=x.AvailableCount,
					UpdateDate=x.UpdateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				})
			.FirstOrDefault();
		}

		// POST api/dishsizeprice/
		public HttpResponseMessage Post([FromBody]DishSizePrice value)
		{
			ServerValidationInfo vi = null;
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

		// PUT api/dishsizeprice/
		public HttpResponseMessage Put([FromBody]DishSizePrice value, string filter = null)
		{
			ServerValidationInfo vi = null;
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
				var old = context.DishSizePrices.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.SizeId = value.SizeId;
				old.DishId = value.DishId;
				old.ExtraPrice = value.ExtraPrice;
				old.AvailableCount = value.AvailableCount;
				old.UpdateDate = value.UpdateDate;
				old.UpdateUserId = value.UpdateUserId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dishsizeprice/
		public bool Delete(string filter = null)
		{
			IQueryable<DishSizePrice> query = context.DishSizePrices;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DishSizePrices.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dishsizeprice/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DishSizePrice> query = context.DishSizePrices.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dishsizeprice/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishSizePrices.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					SizeId=x.SizeId,
					SizeName=x.Size.Name,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					ExtraPrice=x.ExtraPrice,
					AvailableCount=x.AvailableCount,
					UpdateDate=x.UpdateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
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
