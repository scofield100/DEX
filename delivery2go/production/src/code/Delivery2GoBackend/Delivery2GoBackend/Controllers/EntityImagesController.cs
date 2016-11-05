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
	public class EntityImagesController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityImages> queryBuider;

		public EntityImagesController()
            :this(new deliveryContext())
		{
			
		}

        public EntityImagesController(deliveryContext context)
        {
            this.context = context;
            queryBuider = new QueryBuilder<EntityImages>()
            .MapMember("EntityName", x => x.Entity.Name)
            .MapMember("ImageLocation", x => x.Image.Location);
        }

		// GET api/entityimages/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "EntityId,ImageId";
			}
			return queryBuider.CreateQuery(context.EntityImageses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
				}).ToList();
		}

		// GET api/entityimages/get/5
		public object Get(int id)
		{
			return context.EntityImageses.AsNoTracking()
				.Where(x=>x.EntityId == id)
				.Select(x=> new
				{
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					ImageId=x.ImageId,
					ImageLocation=x.Image.Location,
				})
			.FirstOrDefault();
		}

		// POST api/entityimages/
		public HttpResponseMessage Post([FromBody]EntityImages value)
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

		// PUT api/entityimages/
		public HttpResponseMessage Put([FromBody]EntityImages value, string filter = null)
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
				var old = context.EntityImageses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.EntityId = value.EntityId;
				old.ImageId = value.ImageId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/entityimages/
		public bool Delete(string filter = null)
		{
			IQueryable<EntityImages> query = context.EntityImageses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.EntityImageses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/entityimages/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<EntityImages> query = context.EntityImageses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/entityimages/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityImageses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
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
