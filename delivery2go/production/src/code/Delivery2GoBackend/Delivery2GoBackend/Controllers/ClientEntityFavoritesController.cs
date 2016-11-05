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
	public class ClientEntityFavoritesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<ClientEntityFavorites> queryBuider;

		public ClientEntityFavoritesController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<ClientEntityFavorites>()
				.MapMember("ClientName", x => x.Client.Name)
				.MapMember("EntityName", x => x.Entity.Name);
		}

		// GET api/cliententityfavorites/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "ClientId,EntityId";
			}
			return queryBuider.CreateQuery(context.ClientEntityFavoriteses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
				}).ToList();
		}

		// GET api/cliententityfavorites/get/5
		public object Get(int id)
		{
			return context.ClientEntityFavoriteses.AsNoTracking()
				.Where(x=>x.ClientId == id)
				.Select(x=> new
				{
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
				})
			.FirstOrDefault();
		}

		// POST api/cliententityfavorites/
		public HttpResponseMessage Post([FromBody]ClientEntityFavorites value)
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

		// PUT api/cliententityfavorites/
		public HttpResponseMessage Put([FromBody]ClientEntityFavorites value, string filter = null)
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
				var old = context.ClientEntityFavoriteses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.ClientId = value.ClientId;
				old.EntityId = value.EntityId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/cliententityfavorites/
		public bool Delete(string filter = null)
		{
			IQueryable<ClientEntityFavorites> query = context.ClientEntityFavoriteses;
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

		// GET api/cliententityfavorites/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<ClientEntityFavorites> query = context.ClientEntityFavoriteses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/cliententityfavorites/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.ClientEntityFavoriteses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
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
