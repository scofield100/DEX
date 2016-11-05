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
	public class CityController : ApiController
	{

		deliveryContext context;
		QueryBuilder<City> queryBuider;

		public CityController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<City>()
				.MapMember("StateName", x => x.State.Name);
		}

		// GET api/city/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Cities.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Name=x.Name,
					StateId=x.StateId,
					StateName=x.State.Name,
				}).ToList();
		}

		// GET api/city/get/5
		public object Get(int id)
		{
			return context.Cities.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					StateId=x.StateId,
					StateName=x.State.Name,
				})
			.FirstOrDefault();
		}

		// POST api/city/
		public HttpResponseMessage Post([FromBody]City value)
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

		// PUT api/city/
		public HttpResponseMessage Put([FromBody]City value, string filter = null)
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
				var old = context.Cities.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.StateId = value.StateId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/city/
		public bool Delete(string filter = null)
		{
			IQueryable<City> query = context.Cities;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Cities.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/city/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<City> query = context.Cities.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/city/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Cities.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					StateId=x.StateId,
					StateName=x.State.Name,
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
