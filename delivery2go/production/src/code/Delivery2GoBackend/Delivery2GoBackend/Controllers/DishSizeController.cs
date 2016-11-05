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
	public class DishSizeController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DishSize> queryBuider;

		public DishSizeController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DishSize>();
		}

		// GET api/dishsize/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.DishSizes.AsNoTracking(), filter, orderby, skip, top).ToList();
		}

		// GET api/dishsize/get/5
		public object Get(int id)
		{
			return context.DishSizes.AsNoTracking()
				.Where(x=>x.Id == id)
			.FirstOrDefault();
		}

		// POST api/dishsize/
		public HttpResponseMessage Post([FromBody]DishSize value)
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

		// PUT api/dishsize/
		public HttpResponseMessage Put([FromBody]DishSize value, string filter = null)
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
				var old = context.DishSizes.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Code = value.Code;
				old.Name = value.Name;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/dishsize/
		public bool Delete(string filter = null)
		{
			IQueryable<DishSize> query = context.DishSizes;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DishSizes.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/dishsize/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DishSize> query = context.DishSizes.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/dishsize/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DishSizes.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
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
