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
	public class DishImagesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishImages> queryBuider;

		public DishImagesController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishImages>()
				.MapMember("DishName", x => x.Dish.Name)
				.MapMember("ImageLocation", x => x.Image.Location);
		}

		// GET api/dishimages/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "DishId,ImageId";
			}
			return queryBuider.CreateQuery(context.DishImageses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					DishId=x.DishId,
					DishName=x.Dish.Name,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
				}).ToList();
		}

		// GET api/dishimages/get/5
		public object Get(int id)
		{
			return context.DishImageses.AsNoTracking()
				.Where(x=>x.DishId == id)
				.Select(x=> new
				{
					DishId=x.DishId,
					DishName=x.Dish.Name,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
				})
			.FirstOrDefault();
		}

		// POST api/dishimages/
		public HttpResponseMessage Post([FromBody]DishImages value)
		{
			ServerValidationInfo vi = null;
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

		// PUT api/dishimages/
		public HttpResponseMessage Put([FromBody]DishImages value, string filter = null)
		{
			ServerValidationInfo vi = null;
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
				var old = context.DishImageses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.DishId = value.DishId;
				old.ImageId = value.ImageId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dishimages/
		public bool Delete(string filter = null)
		{
			IQueryable<DishImages> query = context.DishImageses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DishImageses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dishimages/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DishImages> query = context.DishImageses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dishimages/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishImageses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					DishId=x.DishId,
					DishName=x.Dish.Name,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
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
